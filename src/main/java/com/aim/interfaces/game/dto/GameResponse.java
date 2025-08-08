package com.aim.interfaces.game.dto;

import com.aim.application.game.dto.GameResult;
import com.aim.domain.YnType;
import com.aim.domain.game.enums.GameMode;
import com.aim.interfaces.member.dto.MemberResponse;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameResponse {
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
	
	private MemberResponse member;
	
	@Enumerated(EnumType.STRING)
	private YnType heartYn;
	
	public static GameResponse from(GameResult gameResult) {
		return GameResponse.builder()
				.gameId(gameResult.getGameId())
				.gameName(gameResult.getGameName())
				.gameMode(gameResult.getGameMode())
				.gameTime(gameResult.getGameTime())
				.endHit(gameResult.getEndHit())
				.endMiss(gameResult.getEndMiss())
				.endLoss(gameResult.getEndLoss())
				.minTargetSize(gameResult.getMinTargetSize())
				.maxTargetSize(gameResult.getMaxTargetSize())
				.hitPoint(gameResult.getHitPoint())
				.missPoint(gameResult.getMissPoint())
				.lossPoint(gameResult.getLossPoint())
				.addTargetSecond(gameResult.getAddTargetSecond())
				.addTargetHit(gameResult.getAddTargetHit())
				.moveSpeed(gameResult.getMoveSpeed())
				.targetLife(gameResult.getTargetLife())
				.bounceNumber(gameResult.getBounceNumber())
				.destroyHit(gameResult.getDestroyHit())
				.member(MemberResponse.from(gameResult.getMember()))
				.build();
	}
}
