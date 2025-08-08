package com.aim.infrastructure.game;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aim.application.game.dto.GameResult;
import com.aim.domain.QGame;
import com.aim.domain.QHeartGame;
import com.aim.domain.QMember;
import com.aim.domain.YnType;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.repository.GameRepository;
import com.aim.domain.member.enums.MemberRole;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepository{
	private final JPAQueryFactory queryFactory;
	private final GameJpaRepository gameJpaRepository;
	
	@Override
	public Game save(Game game) {
		return gameJpaRepository.save(game);
	}

	@Override
	public List<Game> findAll() {
		return gameJpaRepository.findAll();
	}

	@Override
	public Optional<Game> findById(Long gameId) {
		return gameJpaRepository.findById(gameId);
	}

	@Override
	public List<Game> findByAdmin() {
		return gameJpaRepository.findByAdmin();
	}
	
	@Override
	public Optional<Game> findByGameIdAndMember_MemberId(Long gameId, Long memberId) {
		return gameJpaRepository.findByGameIdAndMember_MemberId(gameId, memberId);
	}

	@Override
	public List<GameResult> findMainGame(Long memberId) {
		List<GameResult> gameList = queryFactory
									.select(Projections.constructor(GameResult.class,QGame.game,QHeartGame.heartGame))
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
	public List<GameResult> findByHeartGame(Long memberId) {
		List<GameResult> heartGameList = queryFactory
										.select(Projections.constructor(GameResult.class,QGame.game,QHeartGame.heartGame))
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
	public List<GameResult> findByMember(Long memberId) {
		List<GameResult> memberGameList = queryFactory
										.select(Projections.constructor(GameResult.class,QGame.game,QHeartGame.heartGame))
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
