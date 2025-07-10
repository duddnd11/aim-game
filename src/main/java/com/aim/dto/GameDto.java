package com.aim.dto;

import com.aim.domain.Game;
import com.aim.domain.GameMode;
import com.aim.domain.HeartGame;
import com.aim.domain.YnType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameDto extends BaseDto{
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
	
	private MemberDto member;
	
	@Enumerated(EnumType.STRING)
	private YnType heartYn;
	
	public GameDto() {}
	
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
		
		this.setCreatedDate(game.getCreatedDate());
		this.setModifiedDate(game.getModifiedDate());
		
		this.member= new MemberDto(game.getMember());
	}
	
	public GameDto(Game game, HeartGame heartGame) {
		this(game);
		this.heartYn = heartGame == null ? YnType.N : heartGame.getUseYn();
	}
}
