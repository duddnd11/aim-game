package com.aim.application.game.dto;

import com.aim.application.BaseDto;
import com.aim.domain.YnType;
import com.aim.domain.game.entity.HeartGame;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class HeartGameResult extends BaseDto{
	private Long heartGameId;
	private Long gameId;
	private Long memberId;
	
	@Enumerated(EnumType.STRING)
	private YnType useYn;
	
	public static HeartGameResult from(HeartGame heartGame) {
		return HeartGameResult.builder()
				.heartGameId(heartGame.getHeartGameId())
				.gameId(heartGame.getGame().getGameId())
				.memberId(heartGame.getMember().getMemberId())
				.useYn(heartGame.getUseYn())
				.build();
	}
}
