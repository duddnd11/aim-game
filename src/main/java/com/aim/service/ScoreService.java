package com.aim.service;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.Game;
import com.aim.domain.Member;
import com.aim.domain.Pvp;
import com.aim.domain.Score;
import com.aim.dto.ScoreCountDto;
import com.aim.dto.ScoreDto;
import com.aim.dto.SliceDto;
import com.aim.exception.NotAuthException;
import com.aim.form.ScoreForm;
import com.aim.repository.GameRepository;
import com.aim.repository.MemberRepository;
import com.aim.repository.PvpRepository;
import com.aim.repository.ScoreRepository;
import com.aim.repository.ScoreRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScoreService {
	private final ScoreRepository scoreRepository;
	private final GameRepository gameRepository;
	private final MemberRepository memberRepository;
	private final PvpRepository pvpRepository;
	private final ScoreRepositoryCustom scpreRepositoryCustom;
	
	/**
	 * 게임 스코어 저장
	 * @param scoreForm
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW) // 별도의 트랜잭션으로 관리
	public ScoreDto saveScore(ScoreForm scoreForm, Long memberId, Integer rating) {
		Game game = gameRepository.findById(scoreForm.getGameId()).orElseThrow(IllegalArgumentException::new);
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotAuthException("로그인 정보 없음"));
		
		Optional<Pvp> pvpOp = pvpRepository.findById(scoreForm.getPvpId());
		Score score = pvpOp.map(pvp -> new Score(scoreForm, member, game, pvp))
		                   .orElseGet(() -> new Score(scoreForm, member, game));
		
		member.ratingUpdate(rating);
		
		return new ScoreDto(scoreRepository.save(score));
	}
	
	/**
	 * 스코어 선택
	 * @param scoreId
	 * @return
	 */
	public ScoreDto selectScore(Long scoreId) {
		Score score = scoreRepository.findById(scoreId).orElseThrow();
		return new ScoreDto(score);
	}
	
	/**
	 * 게임종류별 스코어 리스트 (랭킹)
	 * @param gameId
	 * @param page
	 * @return
	 */
	public Slice<ScoreDto> scoreRanking(Long gameId, int page){
		Slice<Score> scoreList = scoreRepository.findByGameId(gameId,PageRequest.of(page-1, 10));
		Slice<ScoreDto> scoreDtoList = scoreList.map(s -> new ScoreDto(s));
		
		return scoreDtoList;
	}
	
	/**
	 * 멤버별 스코어 스탯
	 * @param gameId
	 * @param memberId
	 * @param page
	 * @return
	 */
	public SliceDto<ScoreDto> memberScoreStat(Long gameId, Long memberId, int page){
//		Member member = memberRepository.findById(memberId).orElseThrow();
//		Game game = gameRepository.findById(gameId).orElseThrow();
		
		SliceDto<ScoreDto> scoreStats = scpreRepositoryCustom.findScoreStat(gameId, memberId, page);
		
		return scoreStats;
	}
	
	/**
	 * 멤버별 플레이 횟수
	 * @param memberId
	 * @return
	 */
	public ScoreCountDto memberScoreCount(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		return scoreRepository.findByScoreCount(member);
	}
}
