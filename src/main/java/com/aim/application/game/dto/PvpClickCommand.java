package com.aim.application.game.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PvpClickCommand {
	private Long memberId;
	private String socketSessionId;
	private String circleKey;
	
	@Builder.Default
	private boolean circleActive = true;
	private Long pvpId;
	private List<PvpMatchingMemberDto> matchingUser;
}
