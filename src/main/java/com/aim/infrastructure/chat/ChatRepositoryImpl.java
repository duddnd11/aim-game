package com.aim.infrastructure.chat;

import org.springframework.stereotype.Repository;

import com.aim.domain.chat.entity.Chat;
import com.aim.domain.chat.repository.ChatRepository;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository{
	private final ChatJpaRepository chatJpaRepository;
	
	@Override
	public Chat save(Chat chat) {
		return chatJpaRepository.save(chat);
	}

}
