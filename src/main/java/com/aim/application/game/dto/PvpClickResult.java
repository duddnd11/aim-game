package com.aim.application.game.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PvpClickResult {
	private Long memberId;
	private String socketSessionId;
	private String circleKey;
	private boolean circleActive;
	private Long pvpId;
	private List<PvpMatchingMemberDto> matchingUser;
	
	public static PvpClickResult from(PvpClickCommand pvpClickCommand) {
		return PvpClickResult.builder()
				.memberId(pvpClickCommand.getMemberId())
				.socketSessionId(pvpClickCommand.getSocketSessionId())
				.circleKey(pvpClickCommand.getCircleKey())
				.pvpId(pvpClickCommand.getPvpId())
				.matchingUser(pvpClickCommand.getMatchingUser())
				.build();
	}
	
	public void circleDetroy() {
		this.circleActive = false;
	}
}
