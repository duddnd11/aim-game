package com.aim.interfaces.game.dto;

import com.aim.application.game.dto.ScoreDto;
import com.aim.domain.game.enums.DifficultLevel;
import com.aim.domain.game.enums.PvpResult;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScoreForm {
	private int totalScore;
	private int totalTarget;
	private int hit;
	private int hitScore;
	private int miss;
	private int missScore;
	private int loss;
	private int lossScore;
	private int playTime;
	private int maxCombo;
	private double avgReact;
	private double minReact;
	private double maxReact;
	
	private long gameId;
	private long pvpId;
	private PvpResult pvpResult;
	
	private DifficultLevel difficultLevel;
	
	public ScoreForm(ScoreDto scoreDto, Long gameId ,Long pvpId) {
		this.totalScore=scoreDto.getTotalScore();
		this.totalTarget=scoreDto.getTotalTarget();
		this.hit=scoreDto.getHit();
		this.hitScore=scoreDto.getHitScore();
		this.miss=scoreDto.getMiss();
		this.missScore=scoreDto.getMissScore();
		this.loss=scoreDto.getLossScore();
		this.playTime=scoreDto.getPlayTime();
		this.maxCombo=scoreDto.getMaxCombo();
		this.avgReact=scoreDto.getAvgReact();
		this.minReact=scoreDto.getMinReact();
		this.maxReact=scoreDto.getMaxReact();
		
		this.gameId=gameId;
		this.pvpId=pvpId;
		this.pvpResult=scoreDto.getPvpResult();
	}
}
