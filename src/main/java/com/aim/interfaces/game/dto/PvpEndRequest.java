package com.aim.interfaces.game.dto;

import com.aim.application.game.dto.PvpEndCommand;

import lombok.Getter;

@Getter
public class PvpEndRequest {
	private Long pvpId;
	
	public PvpEndCommand toCommand() {
		return PvpEndCommand.builder()
				.pvpId(pvpId)
				.build();
	}
}
