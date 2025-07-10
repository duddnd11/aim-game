package com.aim.domain;

import com.aim.form.ScoreForm;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Score extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long scoreId;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id")
	private Game game;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pvp_id")
	private Pvp pvp;
	
	@Enumerated(EnumType.STRING)
	private PvpResult pvpResult;
	
	public Score(ScoreForm scoreForm) {
		this.totalScore = scoreForm.getTotalScore();
		this.totalTarget = scoreForm.getTotalTarget();
		this.hit = scoreForm.getHit();
		this.hitScore = scoreForm.getHitScore();
		this.miss = scoreForm.getMiss();
		this.missScore = scoreForm.getMissScore();
		this.loss = scoreForm.getLoss();
		this.lossScore = scoreForm.getLossScore();
		this.playTime = scoreForm.getPlayTime();
		this.maxCombo = scoreForm.getMaxCombo();
		this.avgReact = scoreForm.getAvgReact();
		this.minReact = scoreForm.getMaxReact();
		this.maxReact = scoreForm.getMinReact();
	}
	
	public Score(ScoreForm scoreForm, Member member, Game game) {
		this(scoreForm);
		this.member=member;
		this.game=game;
	}
	
	public Score(ScoreForm scoreForm, Member member, Game game, Pvp pvp) {
		this(scoreForm,member,game);
		this.pvp=pvp;
		this.pvpResult=scoreForm.getPvpResult();
	}
}
