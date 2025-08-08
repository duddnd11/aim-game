package com.aim.application.game.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.game.dto.CircleDto;
import com.aim.application.game.dto.GameDto;
import com.aim.application.game.dto.PvpClickCommand;
import com.aim.application.game.dto.PvpClickResult;
import com.aim.application.game.dto.PvpCommand;
import com.aim.application.game.dto.PvpEndCommand;
import com.aim.application.game.dto.PvpMatchingDto;
import com.aim.application.game.dto.PvpMatchingMemberDto;
import com.aim.application.game.dto.ScoreDto;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.entity.Pvp;
import com.aim.domain.game.enums.EndType;
import com.aim.domain.game.enums.GameMode;
import com.aim.domain.game.enums.MatchType;
import com.aim.domain.game.repository.GameRepository;
import com.aim.domain.game.repository.PvpRepository;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PvpService {
	private final PvpRepository pvpRepository;
	private final SimpMessagingTemplate messagingTemplate;
	private final MemberRepository memberRepository;
	private final GameRepository gameRepository;
	private final Map<Long,ConcurrentLinkedQueue<PvpMatchingMemberDto>> pvpQueMap;
	private final ConcurrentHashMap<Long, PvpMatchingDto> pvpGames;
	private final ConcurrentHashMap<Long, ScheduledFuture<?>> pvpFutureMap;
	private final ScheduledExecutorService scheduler;
	private final PvpScoreService pvpScoreService;
	private final RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 매칭 대기열 큐
	 * @param memberid
	 * @param socketSessionId
	 * @return
	 */
	public List<PvpMatchingMemberDto> pvpMatchingQue(Long memberId, String socketSessionId, PvpCommand pvpCommand) {
		log.info("pvpMatchingQue");
		Member member = memberRepository.findById(memberId).orElseThrow();
		Long gameId = pvpCommand.getGameId();
		
		ConcurrentLinkedQueue<PvpMatchingMemberDto> pvpQue = pvpQueMap.computeIfAbsent(gameId, k -> new ConcurrentLinkedQueue<>());
		PvpMatchingMemberDto pvpMatchingMemberDto = PvpMatchingMemberDto.from(member, socketSessionId);
		if(!pvpQue.contains(pvpMatchingMemberDto)) {
			pvpQue.offer(pvpMatchingMemberDto);
		}
		
		List<PvpMatchingMemberDto> matchingUser = new ArrayList<PvpMatchingMemberDto>();
		
		if(pvpQue.size() >= 2) {
			for(int i=0; i<2; i++) {
				matchingUser.add(pvpQue.poll());
			}
		}
		return matchingUser;
	}
	
	/**
	 * pvp 게임 시작
	 * @param gameDto
	 * @param matchingUser
	 */
	@Transactional
	public void gameStart(PvpCommand pvpCommand, List<PvpMatchingMemberDto> matchingUser, MatchType matchType) {
		log.info("gameStart");
		Game game = gameRepository.findById(pvpCommand.getGameId()).orElseThrow();
		Pvp pvp = new Pvp(game);
		pvpRepository.save(pvp);
		PvpMatchingDto pvpMatching = new PvpMatchingDto(pvp.getPvpId(), game, matchingUser, matchType);
		
		pvpGames.put(pvpMatching.getPvpId(), pvpMatching);
//		redisTemplate.opsForValue().set("pvp:game:"+pvpMatching.getPvpId(), pvpMatching);
		pvpSchedule(pvpMatching);
		
		for(int i=0; i<matchingUser.size(); i++) {
			messagingTemplate.convertAndSendToUser(matchingUser.get(i).getLoginId(),"/pvp/matching",pvpMatching);
		}
	}
	
	/*
	 * 타겟 생성
	 */
	public void targetCreate(PvpMatchingDto pvpMatching) {
		//log.info("targetCreate:"+pvpMatching.getCircleMap().size());
//		PvpMatchingDto pvpMatchingDto = (PvpMatchingDto) redisTemplate.opsForValue().get("pvp:game:"+pvpMatching.getPvpId());
		GameDto gameDto = pvpMatching.getGameDto();
		CircleDto circle = new CircleDto(gameDto);
		ConcurrentHashMap<String, CircleDto> circleMap = pvpMatching.getCircleMap();
		circleMap.put(circle.getCircleKey(),circle);
//		redisTemplate.opsForValue().set("pvp:game:"+pvpMatching.getPvpId(), pvpMatchingDto);
		
		List<PvpMatchingMemberDto> matchingUser = pvpMatching.getMatchingUser();
		for(int i=0; i<matchingUser.size();i++) {
			messagingTemplate.convertAndSendToUser(matchingUser.get(i).getLoginId(),"/pvp/target",circle);
		}
		
		targetState(pvpMatching);
	}
	
	/**
	 * 타겟 클릭
	 * @param pvpClickCommand
	 */
//	@Transactional
	public void targetClick(PvpClickCommand pvpClickCommand) {
		PvpMatchingDto pvpMatchingDto = pvpGames.get(pvpClickCommand.getPvpId());
		log.info("pvpId:"+pvpClickCommand.getPvpId());
//		PvpMatchingDto pvpMatchingDto = (PvpMatchingDto) redisTemplate.opsForValue().get("pvp:game:"+pvpClickCommand.getPvpId());
		Map<String,CircleDto> circleMap = pvpMatchingDto.getCircleMap();
		CircleDto circleDto = null;
		String circleKey = pvpClickCommand.getCircleKey();
		if(circleKey != null) {
			circleDto =  circleMap.get(pvpClickCommand.getCircleKey());
			log.info("circle:"+circleKey+" / "+circleDto);
		}
		List<PvpMatchingMemberDto> matchingUserList = pvpMatchingDto.getMatchingUser();
		GameDto gameDto = pvpMatchingDto.getGameDto();
		PvpClickResult pvpClickResult = PvpClickResult.from(pvpClickCommand);
		
		for(PvpMatchingMemberDto matchingUser : matchingUserList) {
			if(pvpClickCommand.getSocketSessionId().equals(matchingUser.getSocketSessionId())) {
				ScoreDto score = matchingUser.getScore();
				if(circleDto != null) {
					Double reactTime = (double) ((System.currentTimeMillis() - circleDto.getCreatedTime()) / 1000);
					
					if(!circleDto.isHit()) {
						score.react(reactTime);
					}
					
					circleDto.click();
					log.info("destroyHit:"+ circleDto.getClickCount() +","+gameDto.getDestroyHit());
					score.hit(gameDto.getHitPoint());
					if(circleDto.getClickCount() >= gameDto.getDestroyHit()) {
						circleDto.setActive(false);
						pvpClickResult.circleDetroy();
					}
				}else {
					score.miss(gameDto.getMissPoint());
				}
				
				if(pvpMatchingDto.isActive() &&
						((gameDto.getEndHit() > 0 && gameDto.getEndHit() <= score.getHit()) || 
						(gameDto.getEndMiss() > 0 && gameDto.getEndMiss() <= score.getMiss()))) {
					pvpScoreService.gameEnd(PvpEndCommand.of(pvpMatchingDto.getPvpId()), EndType.END, null);
				}
			}
		}
		
//		pvpClickCommand.setMatchingUser(matchingUserList);
//		redisTemplate.opsForValue().set("pvp:game:"+pvpClickCommand.getPvpId(), pvpMatchingDto);
		for(PvpMatchingMemberDto matchingUser : matchingUserList) {
			messagingTemplate.convertAndSendToUser(matchingUser.getLoginId(),"/pvp/click",pvpClickResult);
		}
	}
	
	/**
	 * 타겟 상태 전체 업데이트
	 * @param pvpClickCommand
	 */
	public void pvpState(PvpClickCommand pvpClickCommand) {
		PvpMatchingDto pvpMatchingDto = pvpGames.get(pvpClickCommand.getPvpId());
//		PvpMatchingDto pvpMatchingDto = (PvpMatchingDto) redisTemplate.opsForValue().get("pvp:game:"+pvpClickCommand.getPvpId());
		
		if(pvpMatchingDto != null) {
			List<PvpMatchingMemberDto> matchingUser = pvpClickCommand.getMatchingUser();
			for(int i=0; i<matchingUser.size(); i++) {
				messagingTemplate.convertAndSendToUser(matchingUser.get(i).getLoginId(),"/pvp/state",pvpMatchingDto.getCircleMap());
			}
		}
	}
	
	/**
	 * 타겟 상태 업데이트
	 * @param matchingUser
	 * @param pvpMatching
	 */
	public void targetState(PvpMatchingDto pvpMatching) {
//		PvpMatchingDto pvpMatchingDto = (PvpMatchingDto) redisTemplate.opsForValue().get("pvp:game:"+pvpMatching.getPvpId());
		Map<String,CircleDto> circleMap = pvpMatching.getCircleMap();
		GameDto gameDto = pvpMatching.getGameDto();
		List<PvpMatchingMemberDto> matchingUser = pvpMatching.getMatchingUser();
		
		// Iterator를 사용해 안전하게 순회 및 삭제
		Iterator<Map.Entry<String, CircleDto>> iterator = circleMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, CircleDto> entry = iterator.next();
			CircleDto circleDto = entry.getValue();
			
			if (!circleDto.isActive()) {
				// Iterator를 사용해 안전하게 삭제
				iterator.remove();
				circleDto.setActive(false);
			} else {
				if(gameDto.getGameMode().equals(GameMode.MOVING)) {
					circleDto.move();
				} 
				if(gameDto.getMinTargetSize() != gameDto.getMaxTargetSize()) {
					circleDto.resize();
				}
				if(gameDto.getTargetLife() > 0) {
					circleDto.lifeOut();
				}
			}
			
			for (PvpMatchingMemberDto pvpMember : matchingUser) {
				messagingTemplate.convertAndSendToUser(pvpMember.getLoginId(), "/pvp/target/state", circleDto);
			}
		}
//		redisTemplate.opsForValue().set("pvp:game:"+pvpMatching.getPvpId(), pvpMatchingDto);
	}
	
	/**
	 * pvp 스케쥴
	 * @param pvpMatching
	 */
	public void pvpSchedule(PvpMatchingDto pvpMatching) {
		log.info("pvpSchedule");
		ScheduledFuture<?> pvpFuture = pvpFutureMap.get(pvpMatching.getPvpId());
		if(pvpFuture == null) {
			AtomicLong startTime = new AtomicLong();
			AtomicLong targetTime = new AtomicLong();
			AtomicBoolean startFlag = new AtomicBoolean(true);
			GameDto game = pvpMatching.getGameDto();
			AtomicLong gameTime = new AtomicLong();
			
			AtomicInteger countNumber = new AtomicInteger(5);
			AtomicReference<ScheduledFuture<?>> countDownRef = new AtomicReference<>();
			
			ScheduledFuture<?> countDown = scheduler.scheduleAtFixedRate(() -> {
				if(countNumber.get() > 0) {
					// 5초 카운트 다운
					for (PvpMatchingMemberDto pvpMember : pvpMatching.getMatchingUser()) {
						messagingTemplate.convertAndSendToUser(pvpMember.getLoginId(), "/pvp/countdown", countNumber);
					}
					countNumber.decrementAndGet();
				}else {
					countDownRef.get().cancel(false);
					
					ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
						// 현재 시간
						long currentTime = System.currentTimeMillis();
						
						// 시작 메시지
						if(startFlag.get()) {
							for (PvpMatchingMemberDto pvpMember : pvpMatching.getMatchingUser()) {
								try {
									messagingTemplate.convertAndSendToUser(pvpMember.getLoginId(), "/pvp/start", pvpMatching);
									startTime.set(currentTime);
									targetTime.set(currentTime);
									gameTime.set(currentTime);
								}catch (Exception e) {
									e.printStackTrace();
								}
							}
							startFlag.set(false);
						}
				        
				        // 게임 시간 계산
				        long elapsedTime = (currentTime - startTime.get()) / 1000;  // 경과 시간(초 단위)
				        long remainingTime = game.getGameTime() - elapsedTime;

				        // 1초마다 남은 시간 계산 및 클라이언트로 전송 (정확히 초 단위)
				        long secondsSinceLastSend = (currentTime - gameTime.get()) / 1000;  // 마지막 전송 후 경과 시간(초 단위)

				        if (secondsSinceLastSend >= 1) {
				            for (PvpMatchingMemberDto pvpMember : pvpMatching.getMatchingUser()) {
				                messagingTemplate.convertAndSendToUser(pvpMember.getLoginId(), "/pvp/time", remainingTime);  // 초 단위 전송
				            }
				            gameTime.set(currentTime);  // 마지막 전송 시간 갱신
				        }
						
						// 타겟 생성
						if(currentTime - targetTime.get() >= pvpMatching.getGameDto().getAddTargetSecond() * 1000) {
							targetCreate(pvpMatching);
							targetTime.set(currentTime);
						}
						
						// 타겟 상태 변경
						targetState(pvpMatching);
						
						// 게임 종료시간 초과 게임 종료
						if(currentTime - startTime.get() >= game.getGameTime() * 1000) {
							pvpScoreService.gameEnd(PvpEndCommand.of(pvpMatching.getPvpId()), EndType.END, null);
							countDownRef.get().cancel(false);
						}
						
					}, 0, 2, TimeUnit.MILLISECONDS);
					pvpFutureMap.put(pvpMatching.getPvpId(), future);
				}
				
			}, 0, 1, TimeUnit.SECONDS);
			
			countDownRef.set(countDown);
		}
	}
	
	/**
	 * 매칭 신청 큐 취소
	 * @param memberId
	 * @param socketSessionId
	 * @param gameId
	 */
	public void quitQue(Long memberId, String socketSessionId, Long gameId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		
		ConcurrentLinkedQueue<PvpMatchingMemberDto> pvpQue = pvpQueMap.computeIfAbsent(gameId, k -> new ConcurrentLinkedQueue<>());
		PvpMatchingMemberDto pvpMatchingMemberDto = PvpMatchingMemberDto.from(member, socketSessionId);
		if(pvpQue.contains(pvpMatchingMemberDto)) {
			pvpQue.remove(pvpMatchingMemberDto);
		}
	}
}
