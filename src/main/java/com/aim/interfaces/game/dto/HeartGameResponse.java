package com.aim.interfaces.game.dto;

import com.aim.application.game.dto.HeartGameResult;
import com.aim.domain.YnType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HeartGameResponse {
	private Long heartGameId;
	private Long gameId;
	private Long memberId;
	
	@Enumerated(EnumType.STRING)
	private YnType useYn;
	
	public static HeartGameResponse from(HeartGameResult heartGameResult) {
		return HeartGameResponse.builder()
				.heartGameId(heartGameResult.getHeartGameId())
				.gameId(heartGameResult.getGameId())
				.memberId(heartGameResult.getMemberId())
				.useYn(heartGameResult.getUseYn())
				.build();
	}
}
