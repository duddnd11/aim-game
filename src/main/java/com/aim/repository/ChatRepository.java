package com.aim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat,Long>{

}
