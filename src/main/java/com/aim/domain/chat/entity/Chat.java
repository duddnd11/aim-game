package com.aim.domain.chat.entity;

import com.aim.domain.board.BaseEntity;
import com.aim.domain.chat.enums.ChatType;
import com.aim.domain.game.entity.Pvp;
import com.aim.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Chat extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatId;
	
	private String message;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Enumerated(EnumType.STRING)
	private ChatType chatType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pvp_id")
	private Pvp pvp;
	
	public Chat() {}
	public Chat(String message) {
		this.message = message;
	}
	public Chat(String message, Member member) {
		this(message);
		this.member = member;
	}
	public Chat(String message, Member member, ChatType chatType) {
		this(message, member);
		this.chatType = chatType;
	}
	public Chat(String message, Member member, ChatType chatType, Pvp pvp) {
		this(message, member, chatType);
		this.pvp = pvp;
	}
}
