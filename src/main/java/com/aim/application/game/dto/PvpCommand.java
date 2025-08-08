package com.aim.application.game.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PvpCommand {
	private Long gameId;
	
	public static PvpCommand of(Long gameId) {
		return PvpCommand.builder()
				.gameId(gameId)
				.build();
	}
}
