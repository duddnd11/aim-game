package com.aim.application.game.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PvpEndCommand {
	private Long pvpId;
	
	public static PvpEndCommand of(Long pvpId) {
		return PvpEndCommand.builder()
				.pvpId(pvpId)
				.build();
	}
}
