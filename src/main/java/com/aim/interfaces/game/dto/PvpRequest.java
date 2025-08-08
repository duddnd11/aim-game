package com.aim.interfaces.game.dto;

import com.aim.application.game.dto.PvpCommand;

import lombok.Getter;

@Getter
public class PvpRequest {
	private Long gameId;
	
	public PvpCommand toCommand() {
		return PvpCommand.builder()
				.gameId(gameId)
				.build();
	}
}
