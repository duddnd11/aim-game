package com.aim.domain.game.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClickScoreDto {
	private Long memberId;
	private String socketSessionId;
	private int totalScore;
	private List<PvpMatchingMemberDto> matchingUser;
}
