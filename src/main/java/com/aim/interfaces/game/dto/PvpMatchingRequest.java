package com.aim.interfaces.game.dto;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.aim.application.game.dto.CircleDto;
import com.aim.application.game.dto.GameDto;
import com.aim.application.game.dto.PvpMatchingMemberDto;
import com.aim.domain.game.enums.MatchType;

import lombok.Getter;


@Getter
public class PvpMatchingRequest {
	private Long pvpId;
	private GameDto gameDto;
	private long pvpTime;
	private boolean active;
	private MatchType matchType;
	
	private List<PvpMatchingMemberDto> matchingUser;
	private ConcurrentHashMap<String,CircleDto> circleMap;
}
