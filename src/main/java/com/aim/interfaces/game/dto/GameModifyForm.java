package com.aim.interfaces.game.dto;

import com.aim.annotation.FieldMoreThan;
import com.aim.application.game.dto.GameModifyCommand;
import com.aim.application.game.dto.GameResult;
import com.aim.domain.game.enums.GameMode;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@FieldMoreThan(field="maxTargetSize", compareTo = "minTargetSize", message = "{game.max-more-min}")
public class GameModifyForm {
	
	@NotNull(message = "{game.id.notblank}")
	private Long gameId;
	
	@NotBlank(message = "{game.name.notblank}")
	private String gameName;
	
	@NotNull(message = "{game.mode.notblank}")
	@Enumerated(EnumType.STRING)
	private GameMode gameMode;
	
	@NotNull(message = "{game.time.notblank}")
	@Min(value = 0)
	@Max(value = 3600)
	private int gameTime;
	
	@NotNull(message = "{game.end-hit.notblank}")
	@Min(value = 0)
	@Max(value = 9999)
	private int endHit;
	
	@NotNull(message = "{game.end-miss.notblank}")
	@Min(value = 0)
	@Max(value = 9999)
	private int endMiss;
	
	@NotNull(message = "{game.end-loss.notblank}")
	@Min(value = 0)
	@Max(value = 9999)
	private int endLoss;
	
	@NotNull(message = "{game.min-target-size.notblank}")
	@Min(value = 0)
	@Max(value = 50)
	private int minTargetSize;
	
	@NotNull(message = "{game.max-target-size.notblank}")
	@Min(value = 0)
	@Max(value = 50)
	private int maxTargetSize;
	
	@NotNull(message = "{game.hit-point.notblank}")
	@Min(value = 1)
	@Max(value = 100)
	private int hitPoint;
	
	@NotNull(message = "{game.miss-point.notblank}")
	@Min(value = 0)
	@Max(value = 100)
	private int missPoint;
	
	@NotNull(message = "{game.loss-point.notblank}")
	@Min(value = 0)
	@Max(value = 100)
	private int lossPoint;
	
	@NotNull(message = "{game.add-target-second.notblank}")
	@Min(value = 0)
	@Max(value = 100)
	private int addTargetSecond;
	
	@NotNull(message = "{game.add-target-hit.notblank}")
	@Min(value = 0)
	@Max(value = 100)
	private int addTargetHit;
	
	@NotNull(message = "{game.move-speed.notblank}")
	@Min(value = 0)
	@Max(value = 300)
	private int moveSpeed;
	
	@NotNull(message = "{game.target-life.notblank}")
	@Min(value = 0)
	@Max(value = 300)
	private int targetLife;
	
	@NotNull(message = "{game.bouce-number.notblank}")
	@Min(value = 0)
	@Max(value = 300)
	private int bounceNumber;
	
	@NotNull(message = "{game.destroy-hit.notblank}")
	@Min(value = 1)
	@Max(value = 100)
	private int destroyHit;
	
	public GameModifyForm() {}
	public GameModifyForm(GameResult gameResult) {
		this.gameId = gameResult.getGameId();
		this.gameName = gameResult.getGameName();
		this.gameMode = gameResult.getGameMode();
		this.gameTime = gameResult.getGameTime();
		this.endHit = gameResult.getEndHit();
		this.endMiss = gameResult.getEndMiss();
		this.endLoss = gameResult.getEndLoss();
		this.minTargetSize = gameResult.getMinTargetSize();
		this.maxTargetSize = gameResult.getMaxTargetSize();
		this.hitPoint = gameResult.getHitPoint();
		this.missPoint = gameResult.getMissPoint();
		this.lossPoint = gameResult.getLossPoint();
		this.addTargetSecond = gameResult.getAddTargetSecond();
		this.addTargetHit = gameResult.getAddTargetHit();
		this.moveSpeed = gameResult.getMoveSpeed();
		this.targetLife = gameResult.getTargetLife();
		this.bounceNumber = gameResult.getBounceNumber();
		this.destroyHit = gameResult.getDestroyHit();
	}
	
	public GameModifyCommand toCommand() {
		return GameModifyCommand.builder()
				.gameId(gameId)
				.gameName(gameName)
				.gameMode(gameMode)
				.gameTime(gameTime)
				.endHit(endHit)
				.endMiss(endMiss)
				.endLoss(endLoss)
				.minTargetSize(minTargetSize)
				.maxTargetSize(maxTargetSize)
				.hitPoint(hitPoint)
				.missPoint(missPoint)
				.lossPoint(lossPoint)
				.addTargetSecond(addTargetSecond)
				.addTargetHit(addTargetHit)
				.moveSpeed(moveSpeed)
				.targetLife(targetLife)
				.bounceNumber(bounceNumber)
				.destroyHit(destroyHit)
				.build();
	}
}
