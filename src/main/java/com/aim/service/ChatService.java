package com.aim.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.Chat;
import com.aim.domain.ChatType;
import com.aim.domain.Member;
import com.aim.domain.Pvp;
import com.aim.dto.ChatDto;
import com.aim.dto.MemberDto;
import com.aim.repository.ChatRepository;
import com.aim.repository.MemberRepository;
import com.aim.repository.PvpRepository;

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
	public ChatDto saveMessage(ChatDto chatDto, Long memberId, String socketSessionId){
		Member member = memberRepository.findById(memberId).orElseThrow();
		Pvp pvp = null;
		if(chatDto.getChatType().equals(ChatType.PVP)) {
			pvp = pvpRepository.findById(chatDto.getPvpMatching().getPvpId()).orElseThrow();
		}
		Chat chat = new Chat(chatDto, member, pvp);
		chatRepository.save(chat);
		chatDto.setMember(new MemberDto(chat.getMember()));
		chatDto.setSessionId(socketSessionId);
		return chatDto;
	}
}
