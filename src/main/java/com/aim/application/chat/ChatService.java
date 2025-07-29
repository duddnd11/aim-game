package com.aim.application.chat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.chat.dto.ChatDto;
import com.aim.domain.chat.entity.Chat;
import com.aim.domain.chat.enums.ChatType;
import com.aim.domain.game.entity.Pvp;
import com.aim.domain.member.dto.MemberDto;
import com.aim.domain.member.entity.Member;
import com.aim.infrastructure.chat.ChatRepository;
import com.aim.infrastructure.game.PvpRepository;
import com.aim.infrastructure.member.MemberRepository;

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
