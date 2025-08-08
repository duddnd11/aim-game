package com.aim.application.chat.dto;

import java.util.List;

import com.aim.application.game.dto.PvpMatchingMemberDto;
import com.aim.domain.chat.enums.ChatType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ChatCommand {
	private String message;
	
	private Long memberId;
	
	private String sessionId;
	
	@Enumerated(EnumType.STRING)
	private ChatType chatType;
	
	private Long pvpId;
	
	private List<PvpMatchingMemberDto> matchingUser;
}
