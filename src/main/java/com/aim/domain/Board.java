package com.aim.domain;

import com.aim.form.BoardForm;
import com.aim.form.BoardModifyForm;

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
	public Board(BoardForm boardForm) {
		this.title=boardForm.getTitle();
		this.contents=boardForm.getContents();
		this.type=boardForm.getType();
	}
	
	public Board(BoardForm boardForm,Member member) {
		this(boardForm);
		this.member=member;
	}
	
	public void modify(BoardModifyForm boardForm) {
		this.title=boardForm.getTitle();
		this.contents=boardForm.getContents();
		this.type=boardForm.getType();
	}
}
