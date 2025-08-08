package com.aim.interfaces.game.dto;

import com.aim.annotation.FieldMoreThan;
import com.aim.application.game.dto.GameCommand;
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
public class GameForm {
	
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
	private int minTargetSize=25;
	
	@NotNull(message = "{game.max-target-size.notblank}")
	@Min(value = 0)
	@Max(value = 50)
	private int maxTargetSize=25;
	
	@NotNull(message = "{game.hit-point.notblank}")
	@Min(value = 1)
	@Max(value = 100)
	private int hitPoint = 1;
	
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
	private int destroyHit = 1;
	
	public GameCommand toCommand() {
		return GameCommand.builder()
				.gameName(this.gameName)
				.gameMode(this.gameMode)
				.gameTime(this.gameTime)
				.endHit(this.endHit)
				.endMiss(this.endMiss)
				.endLoss(this.endLoss)
				.minTargetSize(this.minTargetSize)
				.maxTargetSize(this.maxTargetSize)
				.hitPoint(this.hitPoint)
				.missPoint(this.missPoint)
				.lossPoint(this.lossPoint)
				.addTargetHit(this.addTargetSecond)
				.addTargetHit(this.addTargetHit)
				.moveSpeed(this.moveSpeed)
				.targetLife(this.targetLife)
				.bounceNumber(this.bounceNumber)
				.destroyHit(this.destroyHit)
				.build();
	}
}
