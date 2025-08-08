package com.aim.interfaces.chat.dto;

import java.util.List;

import com.aim.application.chat.dto.ChatCommand;
import com.aim.application.game.dto.PvpMatchingMemberDto;
import com.aim.domain.chat.enums.ChatType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChatRequest {
	private String message;
	
	private String sessionId;
	
	@Enumerated(EnumType.STRING)
	private ChatType chatType;
	
	private Long pvpId;
	
	private List<PvpMatchingMemberDto> matchingUser;
	
	public ChatCommand toChatCommand(){
		return ChatCommand.builder()
				.message(this.message)
				.chatType(this.chatType)
				.pvpId(this.pvpId)
				.matchingUser(this.matchingUser)
				.build();
	}
}
