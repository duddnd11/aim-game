package com.aim.application.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.game.dto.PvpMatchingDto;
import com.aim.domain.game.dto.PvpMatchingMemberDto;
import com.aim.domain.game.dto.ScoreDto;
import com.aim.domain.game.enums.EndType;
import com.aim.interfaces.game.dto.ScoreForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PvpScoreService {
	private final ScoreService scoreService;
	private final SimpMessagingTemplate messagingTemplate;
	private final ConcurrentHashMap<Long, PvpMatchingDto> pvpGames;
	private final ConcurrentHashMap<Long, ScheduledFuture<?>> pvpFutureMap;
	private final RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * pvp 게임 종료
	 * @param pvpMatching
	 */
//	@Transactional
	public void gameEnd(PvpMatchingDto pvpMatching, EndType endType, Long memberId) {
//		log.info("gameEnd");
		PvpMatchingDto findPvpMatching = pvpGames.get(pvpMatching.getPvpId());
//		PvpMatchingDto findPvpMatching = (PvpMatchingDto)redisTemplate.opsForValue().get("pvp:game:"+pvpMatching.getPvpId());
		pvpFutureMap.get(findPvpMatching.getPvpId()).cancel(false);
		findPvpMatching.pvpEnd();
		
		Map<String, Object> headers = new HashMap<String,Object>();
		headers.put("endType", endType);
		
		if(endType.equals(EndType.END)) {
			findPvpMatching.pvpResult();
		}else {
			findPvpMatching.pvpResultForfeit(memberId);
		}
		
		List<PvpMatchingMemberDto> matchingUser = findPvpMatching.getMatchingUser();
		for(PvpMatchingMemberDto pvpUser : matchingUser) {
			ScoreDto scoreDto = pvpUser.getScore();
			ScoreForm scoreForm = new ScoreForm(scoreDto,findPvpMatching.getGameDto().getGameId(),findPvpMatching.getPvpId());
			try {
				// 예외처리.. 스코어 저장 실패해도 게임은 정상 종료
				scoreService.saveScore(scoreForm, pvpUser.getMemberId(),pvpUser.getRating());
			}catch (Exception e) {
				log.error("score-error");
			}
			
			messagingTemplate.convertAndSendToUser(pvpUser.getLoginId(),"/pvp/end",findPvpMatching, headers);
		}
		
//		redisTemplate.opsForValue().set("pvp:game:"+pvpMatching.getPvpId(), findPvpMatching);
//		redisTemplate.delete("pvp:game:"+pvpMatching.getPvpId());
		pvpGames.remove(pvpMatching.getPvpId());
	}
	
}
