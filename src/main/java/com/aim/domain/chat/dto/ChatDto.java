package com.aim.domain.chat.dto;

import com.aim.domain.chat.entity.Chat;
import com.aim.domain.chat.enums.ChatType;
import com.aim.domain.game.dto.PvpMatchingDto;
import com.aim.domain.member.dto.MemberDto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ChatDto {
	private String message;
	
	private MemberDto member;
	
	private String sessionId;
	
	@Enumerated(EnumType.STRING)
	private ChatType chatType;
	
	private PvpMatchingDto pvpMatching;
	
	public ChatDto() {}
	
	public ChatDto(Chat chat) {
		this.message = chat.getMessage();
		this.member = new MemberDto(chat.getMember());
		this.chatType = chat.getChatType();
	}
	
	public ChatDto(Chat chat, String sessionId) {
		this(chat);
		this.sessionId = sessionId;
	}
	
}
