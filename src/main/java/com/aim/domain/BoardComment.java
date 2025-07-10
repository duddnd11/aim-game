package com.aim.domain;

import com.aim.form.BoardCommentForm;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class BoardComment extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardCommentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	
	private String contents;
	
	public BoardComment() {}
	public BoardComment(BoardCommentForm boardCommentForm) {
		this.contents=boardCommentForm.getContents();
	}
	
	public BoardComment(BoardCommentForm boardCommentForm,Member member) {
		this(boardCommentForm);
		this.member=member;
	}
	public BoardComment(BoardCommentForm boardCommentForm,Member member,Board board) {
		this(boardCommentForm,member);
		this.board=board;
	}
}
