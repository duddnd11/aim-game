package com.aim.repository;

import java.util.List;

import com.aim.domain.QScore;
import com.aim.dto.ScoreDto;
import com.aim.dto.SliceDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ScoreRepositoryImpl implements ScoreRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	
	public ScoreRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
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
