package com.aim.domain;

import com.aim.form.GameForm;
import com.aim.form.GameModifyForm;

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
import lombok.Getter;

@Entity
@Getter
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
	
	public Game() {}
	
	public Game(GameForm gameForm) {
		this.gameName = gameForm.getGameName();
		this.gameMode = gameForm.getGameMode();
		this.gameTime = gameForm.getGameTime();
		this.endHit = gameForm.getEndHit();
		this.endMiss = gameForm.getEndMiss();
		this.endLoss = gameForm.getEndMiss();
		this.minTargetSize = gameForm.getMinTargetSize();
		this.maxTargetSize = gameForm.getMaxTargetSize();
		this.hitPoint = gameForm.getHitPoint();
		this.missPoint = gameForm.getMissPoint();
		this.lossPoint = gameForm.getLossPoint();
		this.addTargetSecond = gameForm.getAddTargetSecond();
		this.addTargetHit = gameForm.getAddTargetHit();
		this.moveSpeed = gameForm.getMoveSpeed();
		this.targetLife = gameForm.getTargetLife();
		this.bounceNumber = gameForm.getBounceNumber();
		this.destroyHit = gameForm.getDestroyHit();
	}
	
	public Game(GameForm gameForm, Member member) {
		this(gameForm);
		this.member = member;
	}
	
	public void gameUpdate(GameModifyForm gameModifyForm) {
		this.gameName=gameModifyForm.getGameName();
		this.gameMode=gameModifyForm.getGameMode();
		this.gameTime=gameModifyForm.getGameTime();
		this.endHit=gameModifyForm.getEndHit();
		this.endMiss=gameModifyForm.getEndMiss();
		this.endLoss=gameModifyForm.getEndLoss();
		this.minTargetSize=gameModifyForm.getMinTargetSize();
		this.maxTargetSize=gameModifyForm.getMaxTargetSize();
		this.hitPoint=gameModifyForm.getHitPoint();
		this.missPoint=gameModifyForm.getMissPoint();
		this.lossPoint=gameModifyForm.getLossPoint();
		this.addTargetSecond=gameModifyForm.getAddTargetSecond();
		this.addTargetHit=gameModifyForm.getAddTargetHit();
		this.moveSpeed=gameModifyForm.getMoveSpeed();
		this.targetLife=gameModifyForm.getTargetLife();
		this.bounceNumber=gameModifyForm.getBounceNumber();
		this.destroyHit=gameModifyForm.getDestroyHit();
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
