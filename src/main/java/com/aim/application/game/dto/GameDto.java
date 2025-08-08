package com.aim.application.game.dto;

import com.aim.application.member.dto.MemberResult;
import com.aim.domain.YnType;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.entity.HeartGame;
import com.aim.domain.game.enums.GameMode;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@SuperBuilder
//@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GameDto{
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
	
	private MemberResult member;
	
	@Enumerated(EnumType.STRING)
	private YnType heartYn;
	
	public GameDto(Game game) {
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
		this.member = MemberResult.from(game.getMember());
	}
	
	public GameDto(Game game, HeartGame heartGame) {
		this(game);
		this.heartYn = heartGame == null ? YnType.N : heartGame.getUseYn();
	}
	
	/*
	public static GameDto from(Game game) {
		return GameDto.builder()
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
				.createdDate(game.getCreatedDate())
				.modifiedDate(game.getModifiedDate())
				.member(MemberDto.from(game.getMember()))
				.build();
	}
	*/
}
