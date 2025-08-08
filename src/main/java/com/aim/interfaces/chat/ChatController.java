package com.aim.interfaces.chat;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import com.aim.application.chat.dto.ChatResult;
import com.aim.application.chat.service.ChatService;
import com.aim.domain.chat.enums.ChatType;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.chat.dto.ChatRequest;
import com.aim.interfaces.chat.dto.ChatResponse;

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
	public ChatResponse chat(ChatRequest chatRequest, Principal principal, SimpMessageHeaderAccessor headerAccessor) {
		log.info("chat");
		chatRequest = chatRequest.toBuilder()
			    .chatType(ChatType.COMMON)
			    .build();
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		return ChatResponse.from(chatService.saveMessage(chatRequest.toChatCommand(), user.getMemberId(),headerAccessor.getSessionId()));
	}
	
	@MessageMapping("/pvp/chat")
	public void pvpChat(ChatRequest chatRequest, Principal principal, SimpMessageHeaderAccessor headerAccessor) {
		log.info("pvpChat:"+chatRequest);
		chatRequest = chatRequest.toBuilder()
			    .chatType(ChatType.PVP)
			    .build();
		AuthUser user = (AuthUser) ((UsernamePasswordAuthenticationToken)principal).getPrincipal();
		ChatResult chatResult = chatService.saveMessage(chatRequest.toChatCommand(), user.getMemberId(),headerAccessor.getSessionId());
		ChatResponse chatResponse = ChatResponse.from(chatResult);
		for(int i=0; i<2; i++) {
			messagingTemplate.convertAndSendToUser(chatResponse.getPvpMatching().getMatchingUser().get(i).getLoginId(),"/chat/pvp/sendMessage",chatResponse);
		} 
	}
	
}
