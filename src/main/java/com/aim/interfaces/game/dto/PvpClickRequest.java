package com.aim.interfaces.game.dto;

import java.util.List;

import com.aim.application.game.dto.PvpClickCommand;
import com.aim.application.game.dto.PvpMatchingMemberDto;

import lombok.Getter;

@Getter
public class PvpClickRequest {
	private String circleKey;
	private Long pvpId;
	private List<PvpMatchingMemberDto> matchingUser;
	
	public PvpClickCommand toCommand(Long memberId, String socketSessionId) {
		return PvpClickCommand.builder()
				.memberId(memberId)
				.socketSessionId(socketSessionId)
				.circleKey(circleKey)
				.pvpId(pvpId)
				.matchingUser(matchingUser)
				.build();
	}
}
