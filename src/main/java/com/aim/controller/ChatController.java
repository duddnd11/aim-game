package com.aim.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import com.aim.domain.AuthUser;
import com.aim.domain.ChatType;
import com.aim.dto.ChatDto;
import com.aim.dto.PvpMatchingMemberDto;
import com.aim.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatService chatService;
	private final SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/chat")
	@SendTo("/chat/sendMessage") // 구독하고 있는 곳으로 메시지 전송
	public ChatDto chat(ChatDto chatDto, Principal principal, SimpMessageHeaderAccessor headerAccessor) {
		log.info("chat");
		chatDto.setChatType(ChatType.COMMON);
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		return chatService.saveMessage(chatDto, user.getMemberId(),headerAccessor.getSessionId());
	}
	
	@MessageMapping("/pvp/chat")
	public void pvpChat(ChatDto chatDto, Principal principal, SimpMessageHeaderAccessor headerAccessor) {
		log.info("pvpChat:"+chatDto);
		chatDto.setChatType(ChatType.PVP);
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		chatDto = chatService.saveMessage(chatDto, user.getMemberId(),headerAccessor.getSessionId());
		for(int i=0; i<2; i++) {
			messagingTemplate.convertAndSendToUser(chatDto.getPvpMatching().getMatchingUser().get(i).getLoginId(),"/chat/pvp/sendMessage",chatDto);
		} 
	}
	
}
