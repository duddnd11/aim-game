package com.aim.application.game.dto;

import java.util.ArrayList;

import com.aim.application.member.dto.MemberResult;
import com.aim.domain.BaseDto;
import com.aim.domain.game.entity.Score;
import com.aim.domain.game.enums.PvpResult;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@EqualsAndHashCode(callSuper = true)
public class ScoreDto{
	private long scoreId;
	private int totalScore;
	private int totalTarget;
	private int hit;
	private int hitScore;
	private int miss;
	private int missScore;
	private int loss;
	private int lossScore;
	private int playTime;
	private int combo;
	private int maxCombo;
	private double avgReact;
	private double minReact;
	private double maxReact;
	private PvpResult pvpResult;
	
	private ArrayList<Double> reactTimes = new ArrayList<Double>();
	
	private MemberResult member;
	
	public ScoreDto() {}
	
	public ScoreDto(Score score) {
		this.scoreId = score.getScoreId();
		this.totalScore = score.getTotalScore();
		this.totalTarget = score.getTotalTarget();
		this.hit = score.getHit();
		this.hitScore = score.getHitScore();
		this.miss = score.getMiss();
		this.missScore = score.getMissScore();
		this.loss = score.getLoss();
		this.lossScore = score.getLossScore();
		this.playTime = score.getPlayTime();
		this.maxCombo = score.getMaxCombo();
		this.avgReact = score.getAvgReact();
		this.minReact = score.getMinReact();
		this.maxReact = score.getMaxReact();
		this.pvpResult = score.getPvpResult();
		
//		this.setCreatedDate(score.getCreatedDate());
//		this.setModifiedDate(score.getModifiedDate());
		
		this.member=  MemberResult.from(score.getMember());
	}
	
	public void scoreReset() {
		this.scoreId = 0;
		this.totalScore = 0;
		this.totalTarget = 0;
		this.hit = 0;
		this.hitScore = 0;
		this.miss = 0;
		this.missScore = 0;
		this.loss = 0;
		this.lossScore = 0;
		this.playTime = 0;
		this.maxCombo = 0;
		this.avgReact = 0;
		this.minReact = 0;
		this.maxReact = 0;
		this.pvpResult = null;
		this.reactTimes = new ArrayList<Double>();
	}
	
	public void hit(int hitPoint) {
		this.hit++;
		this.hitScore+=hitPoint;
		this.combo++;
		this.totalScore+=hitPoint;
		this.maxCombo = Math.max(maxCombo, combo);
	}
	
	public void miss(int missPoint) {
		this.miss++;
		this.missScore-=missPoint;
		this.combo=0;
		this.totalScore-=missPoint;
	}
	
	public void react(Double reactTime) {
		this.reactTimes.add(reactTime);
		this.maxReact = Math.max(maxReact, reactTime);
		this.minReact = Math.min(minReact, reactTime);
		this.avgReact = 0;
	}
}
