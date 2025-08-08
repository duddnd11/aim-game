package com.aim.application.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.chat.dto.ChatCommand;
import com.aim.application.chat.dto.ChatResult;
import com.aim.domain.chat.entity.Chat;
import com.aim.domain.chat.enums.ChatType;
import com.aim.domain.chat.repository.ChatRepository;
import com.aim.domain.game.entity.Pvp;
import com.aim.domain.game.repository.PvpRepository;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
	
	private final ChatRepository chatRepository;
	private final MemberRepository memberRepository;
	private final PvpRepository pvpRepository;

	/**
	 *	채팅 메시지 저장 
	 * @param message
	 * @param memberId
	 */
	@Transactional
	public ChatResult saveMessage(ChatCommand chatCommand, Long memberId, String socketSessionId){
		Member member = memberRepository.findById(memberId).orElseThrow();
		Pvp pvp = null;
		if(chatCommand.getChatType().equals(ChatType.PVP)) {
			pvp = pvpRepository.findById(chatCommand.getPvpId()).orElseThrow();
		}
		Chat chat = new Chat(chatCommand.getMessage(), member, chatCommand.getChatType(), pvp);
		chat = chatRepository.save(chat);
		return ChatResult.from(chat, socketSessionId);
	}
}
