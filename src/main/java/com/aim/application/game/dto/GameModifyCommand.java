package com.aim.application.game.dto;

import com.aim.domain.game.enums.GameMode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameModifyCommand {
	private Long gameId;
	private String gameName;
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
}
