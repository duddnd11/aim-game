package com.aim.dto;

import com.aim.domain.HeartGame;
import com.aim.domain.YnType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HeartGameDto extends BaseDto{
	private Long heartGameId;
	private Long gameId;
	private Long memberId;
	
	@Enumerated(EnumType.STRING)
	private YnType useYn;
	
	public HeartGameDto() {}
	
	public HeartGameDto(HeartGame heartGame) {
		this.heartGameId = heartGame.getHeartGameId();
		this.gameId = heartGame.getGame().getGameId();
		this.memberId = heartGame.getMember().getMemberId();
		this.useYn = heartGame.getUseYn();
	}
}
