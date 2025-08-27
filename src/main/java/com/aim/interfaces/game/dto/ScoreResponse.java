package com.aim.interfaces.game.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.aim.application.BaseDto;
import com.aim.application.game.dto.ScoreResult;
import com.aim.domain.file.dto.UploadFileDto;
import com.aim.domain.file.enums.UploadFileType;
import com.aim.domain.game.enums.PvpResult;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ScoreResponse extends BaseDto{
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
	
	public static ScoreResponse from(ScoreResult scoreResult) {
		return ScoreResponse.builder()
				.scoreId(scoreResult.getScoreId())
				.totalScore(scoreResult.getTotalScore())
				.totalTarget(scoreResult.getTotalTarget())
				.hit(scoreResult.getHit())
				.hitScore(scoreResult.getHitScore())
				.miss(scoreResult.getMiss())
				.missScore(scoreResult.getMissScore())
				.loss(scoreResult.getLoss())
				.lossScore(scoreResult.getLossScore())
				.playTime(scoreResult.getPlayTime())
				.maxCombo(scoreResult.getMaxCombo())
				.avgReact(scoreResult.getAvgReact())
				.minReact(scoreResult.getMinReact())
				.maxReact(scoreResult.getMaxReact())
				.pvpResult(scoreResult.getPvpResult())
				.createdDate(scoreResult.getCreatedDate())
				.modifiedDate(scoreResult.getModifiedDate())
				.memberId(scoreResult.getMemberId())
				.nickname(scoreResult.getNickname())
				.profileImg(scoreResult.getProfileImg())
				.build();
	}
}
