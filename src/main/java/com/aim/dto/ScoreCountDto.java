package com.aim.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreCountDto {
	private long gameCount;
	private long singleGameCount;
	private long pvpGameCount;
}
