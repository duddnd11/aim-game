package com.aim.infrastructure.game;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.aim.domain.QScore;
import com.aim.domain.SliceDto;
import com.aim.domain.game.dto.ScoreCountDto;
import com.aim.domain.game.dto.ScoreDto;
import com.aim.domain.game.entity.Score;
import com.aim.domain.game.repository.ScoreRepository;
import com.aim.domain.member.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScoreRepositoryImpl implements ScoreRepository{
	private final JPAQueryFactory queryFactory;
	private final ScoreJpaRepository scoreJpaRepository;
	
	@Override
	public Score save(Score score) {
		return scoreJpaRepository.save(score);
	}

	@Override
	public Optional<Score> findById(Long scoreId) {
		return scoreJpaRepository.findById(scoreId);
	}

	@Override
	public Slice<Score> findByGameId(Long gameId, Pageable pageable) {
		return scoreJpaRepository.findByGameId(gameId, pageable);
	}

	@Override
	public Slice<Score> findByGame_GameIdAndMember_MemberId(Long gameId, Long memberId, Pageable pageable) {
		return scoreJpaRepository.findByGame_GameIdAndMember_MemberId(gameId, memberId, pageable);
	}

	@Override
	public ScoreCountDto findByScoreCount(Member member) {
		return scoreJpaRepository.findByScoreCount(member);
	}
	
	@Override
	public SliceDto<ScoreDto> findScoreStat(Long gameId, Long memberId, int page) {
		page = page < 0 ? 0 : page;
		int pageSize = 10;
		
		List<ScoreDto> scoreList = queryFactory
						.select(Projections.constructor(ScoreDto.class,QScore.score))
						.from(QScore.score)
						.where(QScore.score.game.gameId.eq(gameId).and(QScore.score.member.memberId.eq(memberId)).and(QScore.score.pvp.isNull()))
						.orderBy(QScore.score.scoreId.desc())
						.offset(page)
						.limit(pageSize+1)
						.fetch();
		
	    boolean hasNext = page == 0 ? false : true; 
	    boolean hasPrevious = scoreList.size() > pageSize;
	    
	    // 추가로 조회된 1개의 데이터를 제거하여 정확한 개수로 맞춤
	    if (hasPrevious) {
	        scoreList.remove(scoreList.size() - 1);
	    }
	    
	    SliceDto<ScoreDto> scoreStatDto = new SliceDto<ScoreDto>(scoreList,hasNext,hasPrevious);
	    return scoreStatDto;
	}

}	
