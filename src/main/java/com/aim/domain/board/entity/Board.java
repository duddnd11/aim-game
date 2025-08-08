package com.aim.domain.board.entity;

import com.aim.domain.board.BaseEntity;
import com.aim.domain.board.enums.BoardType;
import com.aim.domain.member.entity.Member;
import com.aim.interfaces.board.dto.BoardForm;
import com.aim.interfaces.board.dto.BoardModifyForm;

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
public class Board extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	private String title;
	
	private String contents;
	
	@Enumerated(EnumType.STRING)
	private BoardType type;
	
	public Board() {}
	public Board(String title, String contents, BoardType type) {
		this.title=title;
		this.contents=contents;
		this.type=type;
	}
	
	public Board(String title, String contents, BoardType type, Member member) {
		this(title, contents, type);
		this.member=member;
	}
	
	public void modify(String title, String contents, BoardType type) {
		this.title=title;
		this.contents=contents;
		this.type=type;
	}
}
