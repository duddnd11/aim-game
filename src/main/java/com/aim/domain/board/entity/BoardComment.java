package com.aim.domain.board.entity;

import com.aim.domain.board.BaseEntity;
import com.aim.domain.member.entity.Member;

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
	public BoardComment(String contents) {
		this.contents=contents;
	}
	
	public BoardComment(String contents, Member member) {
		this(contents);
		this.member=member;
	}
	public BoardComment(String contents, Member member, Board board) {
		this(contents, member);
		this.board=board;
	}
}
