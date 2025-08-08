package com.aim.application.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreCountResult {
	private long gameCount;
	private long singleGameCount;
	private long pvpGameCount;
}
