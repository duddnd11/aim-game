package com.aim.application.game.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.aim.application.BaseDto;
import com.aim.domain.file.dto.UploadFileDto;
import com.aim.domain.file.enums.UploadFileType;
import com.aim.domain.game.entity.Score;
import com.aim.domain.game.enums.PvpResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class ScoreResult extends BaseDto{
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
	private int maxCombo;
	private double avgReact;
	private double minReact;
	private double maxReact;
	private PvpResult pvpResult;
	private Long memberId;
	private String nickname;
	private List<UploadFileDto> profileImg;
	
	public ScoreResult(Score score) {
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
		this.memberId = score.getMember().getMemberId();
		this.nickname = score.getMember().getNickname();
		this.profileImg = score.getMember().getProfileImg().stream()
				.filter(uf -> uf.getType().equals(UploadFileType.PROFILE))
				.map(uf -> new UploadFileDto(uf))
				.collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
		            Collections.reverse(list);
		            return list;
		        }));
	}
	
	public static ScoreResult from(Score score) {
		return ScoreResult.builder()
				.scoreId(score.getScoreId())
				.totalScore(score.getTotalScore())
				.totalTarget(score.getTotalTarget())
				.hit(score.getHit())
				.hitScore(score.getHitScore())
				.miss(score.getMiss())
				.missScore(score.getMissScore())
				.loss(score.getLoss())
				.lossScore(score.getLossScore())
				.playTime(score.getPlayTime())
				.maxCombo(score.getMaxCombo())
				.avgReact(score.getAvgReact())
				.minReact(score.getMinReact())
				.maxReact(score.getMaxReact())
				.pvpResult(score.getPvpResult())
				.createdDate(score.getCreatedDate())
				.modifiedDate(score.getModifiedDate())
				.memberId(score.getMember().getMemberId())
				.nickname(score.getMember().getNickname())
				.profileImg(score.getMember().getProfileImg().stream()
							.filter(uf -> uf.getType().equals(UploadFileType.PROFILE))
							.map(uf -> new UploadFileDto(uf))
							.collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
					            Collections.reverse(list);
					            return list;
					        })))
				.build();
	}
}
