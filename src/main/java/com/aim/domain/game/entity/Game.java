package com.aim.domain.game.entity;

import com.aim.domain.YnType;
import com.aim.domain.board.BaseEntity;
import com.aim.domain.game.enums.GameMode;
import com.aim.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Game extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gameId;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Enumerated(EnumType.STRING)
	private YnType useYn;
	
	public Game(String gameName, GameMode gameMode, int gameTime, int endHit, int endMiss, int endLoss
			, int minTargetSize, int maxTargetSize, int hitPoint, int missPoint, int lossPoint
			, int addTargetSecond, int addTargetHit, int moveSpeed, int targetLife, int bounceNumber, int destroyHit) {
		this.gameName = gameName;
		this.gameMode = gameMode;
		this.gameTime = gameTime;
		this.endHit = endHit;
		this.endMiss = endMiss;
		this.endLoss = endLoss;
		this.minTargetSize = maxTargetSize;
		this.maxTargetSize = maxTargetSize;
		this.hitPoint = hitPoint;
		this.missPoint = missPoint;
		this.lossPoint = lossPoint;
		this.addTargetSecond = addTargetSecond;
		this.addTargetHit = addTargetHit;
		this.moveSpeed = moveSpeed;
		this.targetLife = targetLife;
		this.bounceNumber = bounceNumber;
		this.destroyHit = destroyHit;
	}
	
	public Game(String gameName, GameMode gameMode, int gameTime, int endHit, int endMiss, int endLoss
			, int minTargetSize, int maxTargetSize, int hitPoint, int missPoint, int lossPoint
			, int addTargetSecond, int addTargetHit, int moveSpeed, int targetLife, int bounceNumber, int destroyHit
			, Member member) {
		this(gameName, gameMode, gameTime, endHit, endMiss, endLoss, minTargetSize, maxTargetSize, hitPoint, missPoint, lossPoint
				, addTargetSecond, addTargetHit, moveSpeed, targetLife, bounceNumber, destroyHit);
		this.member = member;
	}
	
	public void gameUpdate(String gameName, GameMode gameMode, int gameTime, int endHit, int endMiss, int endLoss
			, int minTargetSize, int maxTargetSize, int hitPoint, int missPoint, int lossPoint
			, int addTargetSecond, int addTargetHit, int moveSpeed, int targetLife, int bounceNumber, int destroyHit) {
		this.gameName = gameName;
		this.gameMode = gameMode;
		this.gameTime = gameTime;
		this.endHit = endHit;
		this.endMiss = endMiss;
		this.endLoss = endLoss;
		this.minTargetSize = maxTargetSize;
		this.maxTargetSize = maxTargetSize;
		this.hitPoint = hitPoint;
		this.missPoint = missPoint;
		this.lossPoint = lossPoint;
		this.addTargetSecond = addTargetSecond;
		this.addTargetHit = addTargetHit;
		this.moveSpeed = moveSpeed;
		this.targetLife = targetLife;
		this.bounceNumber = bounceNumber;
		this.destroyHit = destroyHit;
	}
	
	public void deleteGame() {
		this.useYn = YnType.N;
	}
	
	@PrePersist
    public void prePersist() {
        if (this.useYn == null) {
            this.useYn = YnType.Y;
        }
    }
}
