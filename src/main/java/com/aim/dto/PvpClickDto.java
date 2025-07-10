package com.aim.dto;

import java.util.List;

import lombok.Data;

@Data
public class PvpClickDto {
	private Long memberId;
	private String socketSessionId;
	private String circleKey;
	private boolean circleActive = true;
	private Long pvpId;
	private List<PvpMatchingMemberDto> matchingUser;	
}
