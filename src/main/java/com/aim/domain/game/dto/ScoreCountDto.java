package com.aim.domain.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreCountDto {
	private long gameCount;
	private long singleGameCount;
	private long pvpGameCount;
}
