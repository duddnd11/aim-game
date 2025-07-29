package com.aim.infrastructure.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat,Long>{

}
