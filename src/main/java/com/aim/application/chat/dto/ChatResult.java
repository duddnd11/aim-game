package com.aim.application.chat.dto;

import java.util.List;

import com.aim.application.game.dto.PvpMatchingMemberDto;
import com.aim.domain.chat.entity.Chat;
import com.aim.domain.chat.enums.ChatType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatResult {
	private String message;
	
	private String loginId;
	
	private String sessionId;
	
	@Enumerated(EnumType.STRING)
	private ChatType chatType;
	
	private Long pvpId;
	
	private List<PvpMatchingMemberDto> matchingUser;
	
	public static ChatResult from(Chat chat, String sessionId) {
		return ChatResult.builder()
				.message(chat.getMessage())
				.loginId(chat.getMember().getLoginId())
				.chatType(chat.getChatType())
				.sessionId(sessionId)
				.build();
	}
}
