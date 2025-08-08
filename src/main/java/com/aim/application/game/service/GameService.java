package com.aim.application.game.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.game.dto.GameCommand;
import com.aim.application.game.dto.GameModifyCommand;
import com.aim.application.game.dto.GameResult;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.repository.GameRepository;
import com.aim.domain.member.entity.AuthUser;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {
	private final GameRepository gameRepository;
	private final MemberRepository memberRepository;
	
	/**
	 * 게임 등록
	 * @param gameForm
	 * @return
	 */
	@Transactional
	public GameResult makeGame(GameCommand gameCommand, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		Game game = new Game(gameCommand.getGameName(), gameCommand.getGameMode(), gameCommand.getGameTime()
				, gameCommand.getEndHit(), gameCommand.getEndMiss(), gameCommand.getEndLoss(), gameCommand.getMinTargetSize(), gameCommand.getMaxTargetSize()
				, gameCommand.getHitPoint(), gameCommand.getMissPoint(), gameCommand.getLossPoint(), gameCommand.getAddTargetSecond(), gameCommand.getAddTargetHit()
				, gameCommand.getMoveSpeed(), gameCommand.getTargetLife(), gameCommand.getBounceNumber(), gameCommand.getDestroyHit(), member);
		return GameResult.from(gameRepository.save(game));
	}
	
	/**
	 * 게임 선택
	 * @param gameId
	 * @return
	 */
	public GameResult selectGame(Long gameId) {
		Game game = gameRepository.findById(gameId).orElseThrow();
		return GameResult.from(game);
	}
	
	public GameResult selectGame(Long gameId, Long memberId) {
		Game game = gameRepository.findByGameIdAndMember_MemberId(gameId,memberId).orElseThrow();
		return GameResult.from(game);
	}
	
	/**
	 * 전체 게임 리스트
	 * @return
	 */
	public List<GameResult> findGameAll(){
		List<Game> gameList = gameRepository.findAll();
		List<GameResult> gameResultList = gameList.stream()
				.map(g -> GameResult.from(g))
				.collect(Collectors.toList());
		
		return gameResultList;
	}
	
	/**
	 * 게임 수정
	 * @param gameModifyForm
	 * @return
	 */
	@Transactional
	public GameResult gameModify(GameModifyCommand gameModifyCommand) {
		Game game = gameRepository.findById(gameModifyCommand.getGameId()).orElseThrow();
		game.gameUpdate(gameModifyCommand.getGameName(), gameModifyCommand.getGameMode(), gameModifyCommand.getGameTime()
				, gameModifyCommand.getEndHit(), gameModifyCommand.getEndMiss(), gameModifyCommand.getEndLoss(), gameModifyCommand.getMinTargetSize()
				, gameModifyCommand.getMaxTargetSize(), gameModifyCommand.getHitPoint(), gameModifyCommand.getMissPoint(), gameModifyCommand.getLossPoint()
				, gameModifyCommand.getAddTargetSecond(), gameModifyCommand.getAddTargetHit(), gameModifyCommand.getMoveSpeed()
				, gameModifyCommand.getTargetLife(), gameModifyCommand.getBounceNumber(), gameModifyCommand.getDestroyHit());
		
		return GameResult.from(game);
	}
	
	/**
	 * 사용자 생성 게임 목록
	 * @param memberId
	 * @return
	 */
	public List<GameResult> findByUser(Long memberId){
		List<GameResult> gameResultList = gameRepository.findByMember(memberId);
		
		return gameResultList;
	}
	
	/**
	 * 관리자 생성 게임 리스트 (메인 페이지)
	 * @return
	 */
	public List<GameResult> findMainGame(AuthUser user){
		List<GameResult> gameResultList;
		if(user == null) {
			List<Game> gameList = gameRepository.findByAdmin();
			gameResultList = gameList.stream()
					.map(g -> GameResult.from(g))
					.collect(Collectors.toList());
		}else {
			gameResultList = gameRepository.findMainGame(user.getMemberId());
		}
		return gameResultList;
	}
	
	/**
	 * 게임 삭제 (상태 변경)
	 * @param gameId
	 * @param memberId
	 */
	@Transactional
	public void deleteGame(Long gameId, Long memberId) {
		Game game = gameRepository.findByGameIdAndMember_MemberId(gameId, memberId).orElseThrow();
		
		game.deleteGame();
	}
	
	/**
	 * 즐겨찾기 게임 찾기
	 * @param memberId
	 * @return
	 */
	public List<GameResult> findByHeart(Long memberId){
		List<GameResult> heartGameList = gameRepository.findByHeartGame(memberId);
		
		return heartGameList;
	}

}
