package com.aim.application.game.dto;

import com.aim.application.BaseDto;
import com.aim.domain.YnType;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.entity.HeartGame;
import com.aim.domain.game.enums.GameMode;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GameResult extends BaseDto{
	private long gameId;
	private String gameName;
	@Enumerated(EnumType.STRING)
	private GameMode gameMode;
	private int gameTime;
	private int endHit;
	private int endMiss;
	private int endLoss;
	private int minTargetSize;
	private int maxTargetSize;
	private int hitPoint;
	private int missPoint;
	private int lossPoint;
	private int addTargetSecond;
	private int addTargetHit;
	private int moveSpeed;
	private int targetLife;
	private int bounceNumber;
	private int destroyHit;
	
	@Enumerated(EnumType.STRING)
	private YnType heartYn;
	
	public static GameResult from(Game game) {
		return GameResult.builder()
				.gameId(game.getGameId())
				.gameName(game.getGameName())
				.gameMode(game.getGameMode())
				.gameTime(game.getGameTime())
				.endHit(game.getEndHit())
				.endMiss(game.getEndMiss())
				.endLoss(game.getEndLoss())
				.minTargetSize(game.getMinTargetSize())
				.maxTargetSize(game.getMaxTargetSize())
				.hitPoint(game.getHitPoint())
				.missPoint(game.getMissPoint())
				.lossPoint(game.getLossPoint())
				.addTargetSecond(game.getAddTargetSecond())
				.addTargetHit(game.getAddTargetHit())
				.moveSpeed(game.getMoveSpeed())
				.targetLife(game.getTargetLife())
				.bounceNumber(game.getBounceNumber())
				.destroyHit(game.getDestroyHit())
				.build();
	}
	
	public GameResult(Game game, HeartGame heartGame) {
		this.gameId = game.getGameId();
		this.gameName = game.getGameName();
		this.gameMode = game.getGameMode();
		this.gameTime = game.getGameTime();
		this.endHit = game.getEndHit();
		this.endMiss = game.getEndMiss();
		this.endLoss = game.getEndLoss();
		this.minTargetSize = game.getMinTargetSize();
		this.maxTargetSize = game.getMaxTargetSize();
		this.hitPoint = game.getHitPoint();
		this.missPoint = game.getMissPoint();
		this.lossPoint = game.getLossPoint();
		this.addTargetSecond = game.getAddTargetSecond();
		this.addTargetHit = game.getAddTargetHit();
		this.moveSpeed = game.getMoveSpeed();
		this.targetLife = game.getTargetLife();
		this.bounceNumber = game.getBounceNumber();
		this.destroyHit = game.getDestroyHit();
		this.heartYn = heartGame == null ? YnType.N : heartGame.getUseYn();
	}
}
