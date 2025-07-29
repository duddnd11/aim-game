package com.aim.infrastructure.game;

import java.util.List;

import com.aim.domain.QGame;
import com.aim.domain.QHeartGame;
import com.aim.domain.QMember;
import com.aim.domain.YnType;
import com.aim.domain.game.dto.GameDto;
import com.aim.domain.member.enums.MemberRole;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class GameRepositoryImpl implements GameRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	
	public GameRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<GameDto> findMainGame(Long memberId) {
		List<GameDto> gameList = queryFactory
									.select(Projections.constructor(GameDto.class,QGame.game,QHeartGame.heartGame))
									.from(QGame.game)
									.leftJoin(QHeartGame.heartGame).on(QGame.game.eq(QHeartGame.heartGame.game)
											.and(QHeartGame.heartGame.member.memberId.eq(memberId))
											)
									.innerJoin(QMember.member).on(QGame.game.member.eq(QMember.member)
											.and(QMember.member.role.eq(MemberRole.ROLE_ADMIN))
											)
									.where(QGame.game.useYn.eq(YnType.Y))
									.fetch();
		return gameList;
	}

	@Override
	public List<GameDto> findByHeartGame(Long memberId) {
		List<GameDto> heartGameList = queryFactory
										.select(Projections.constructor(GameDto.class,QGame.game,QHeartGame.heartGame))
										.from(QGame.game)
										.innerJoin(QHeartGame.heartGame)
												.on(QGame.game.eq(QHeartGame.heartGame.game)
														.and(QHeartGame.heartGame.member.memberId.eq(memberId))
														.and(QHeartGame.heartGame.useYn.eq(YnType.Y)))
										.where(QGame.game.useYn.eq(YnType.Y))
										.fetch();
		
		return heartGameList;
	}

	@Override
	public List<GameDto> findByMember(Long memberId) {
		List<GameDto> memberGameList = queryFactory
										.select(Projections.constructor(GameDto.class,QGame.game,QHeartGame.heartGame))
										.from(QGame.game)
										.leftJoin(QHeartGame.heartGame)
												.on(QGame.game.eq(QHeartGame.heartGame.game)
														.and(QHeartGame.heartGame.member.memberId.eq(memberId)))
										.innerJoin(QMember.member).on(QGame.game.member.eq(QMember.member))
										.where(QGame.game.member.memberId.eq(memberId).and(QGame.game.useYn.eq(YnType.Y)))
										.fetch();
				
		return memberGameList;
	}

}
