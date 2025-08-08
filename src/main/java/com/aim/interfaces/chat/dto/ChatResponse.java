package com.aim.interfaces.chat.dto;

import com.aim.application.chat.dto.ChatResult;
import com.aim.application.game.dto.PvpMatchingDto;
import com.aim.domain.chat.enums.ChatType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResponse {
	private String message;
	
	private String loginId;
	
	private String sessionId;
	
	@Enumerated(EnumType.STRING)
	private ChatType chatType;
	
	private PvpMatchingDto pvpMatching;
	
	public static ChatResponse from(ChatResult chatResult) {
		return ChatResponse.builder()
				.message(chatResult.getMessage())
				.loginId(chatResult.getLoginId())
				.sessionId(chatResult.getSessionId())
				.chatType(chatResult.getChatType())
				.build();
	}
}
