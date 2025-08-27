package com.aim.application.game.service;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.SliceDto;
import com.aim.application.game.dto.ScoreCountResult;
import com.aim.application.game.dto.ScoreResult;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.entity.Pvp;
import com.aim.domain.game.entity.Score;
import com.aim.domain.game.repository.GameRepository;
import com.aim.domain.game.repository.PvpRepository;
import com.aim.domain.game.repository.ScoreRepository;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;
import com.aim.exception.NotAuthException;
import com.aim.interfaces.game.dto.ScoreForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {
	private final ScoreRepository scoreRepository;
	private final GameRepository gameRepository;
	private final MemberRepository memberRepository;
	private final PvpRepository pvpRepository;
	
	/**
	 * 게임 스코어 저장
	 * @param scoreForm
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW) // 별도의 트랜잭션으로 관리
	public ScoreResult saveScore(ScoreForm scoreForm, Long memberId, Integer rating) {
		Game game = gameRepository.findById(scoreForm.getGameId()).orElseThrow(IllegalArgumentException::new);
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotAuthException("로그인 정보 없음"));
		
		Optional<Pvp> pvpOp = pvpRepository.findById(scoreForm.getPvpId());
		Score score = pvpOp.map(pvp -> new Score(scoreForm, member, game, pvp))
		                   .orElseGet(() -> new Score(scoreForm, member, game));
		
		member.ratingUpdate(rating);
		
		return ScoreResult.from(scoreRepository.save(score));
	}
	
	/**
	 * 스코어 선택
	 * @param scoreId
	 * @return
	 */
	public ScoreResult selectScore(Long scoreId) {
		Score score = scoreRepository.findById(scoreId).orElseThrow();
		return ScoreResult.from(score);
	}
	
	/**
	 * 게임종류별 스코어 리스트 (랭킹)
	 * @param gameId
	 * @param page
	 * @return
	 */
	public Slice<ScoreResult> scoreRanking(Long gameId, int page){
		Slice<Score> scoreList = scoreRepository.findByGameId(gameId,PageRequest.of(page-1, 10));
		Slice<ScoreResult> scoreResultList = scoreList.map(s -> ScoreResult.from(s));
		
		return scoreResultList;
	}
	
	/**
	 * 멤버별 스코어 스탯
	 * @param gameId
	 * @param memberId
	 * @param page
	 * @return
	 */
	public SliceDto<ScoreResult> memberScoreStat(Long gameId, Long memberId, int page){
//		Member member = memberRepository.findById(memberId).orElseThrow();
//		Game game = gameRepository.findById(gameId).orElseThrow();
		
		SliceDto<ScoreResult> scoreStats = scoreRepository.findScoreStat(gameId, memberId, page);
		
		return scoreStats;
	}
	
	/**
	 * 멤버별 플레이 횟수
	 * @param memberId
	 * @return
	 */
	public ScoreCountResult memberScoreCount(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		return scoreRepository.findByScoreCount(member);
	}
}
