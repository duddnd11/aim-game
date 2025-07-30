package com.aim.infrastructure.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.chat.entity.Chat;

public interface ChatJpaRepository extends JpaRepository<Chat,Long>{

}
