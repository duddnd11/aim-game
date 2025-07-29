package com.aim.application.game;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.SliceDto;
import com.aim.domain.game.dto.ScoreCountDto;
import com.aim.domain.game.dto.ScoreDto;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.entity.Pvp;
import com.aim.domain.game.entity.Score;
import com.aim.domain.member.entity.Member;
import com.aim.exception.NotAuthException;
import com.aim.infrastructure.game.GameRepository;
import com.aim.infrastructure.game.PvpRepository;
import com.aim.infrastructure.game.ScoreRepository;
import com.aim.infrastructure.game.ScoreRepositoryCustom;
import com.aim.infrastructure.member.MemberRepository;
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
