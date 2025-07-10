package com.aim.dto;

import com.aim.domain.BoardComment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BoardCommentDto extends BaseDto{
	private Long boardCommentId;
	
	private MemberDto member;
	
	private BoardDto board;
	
	private String contents;
	
	public BoardCommentDto() {}
	
	public BoardCommentDto(BoardComment boardComment) {
		this.boardCommentId=boardComment.getBoardCommentId();
		this.contents=boardComment.getContents();
		this.member=new MemberDto(boardComment.getMember());
		this.board=new BoardDto(boardComment.getBoard());
		this.setCreatedDate(boardComment.getCreatedDate());
		this.setModifiedDate(boardComment.getModifiedDate());
	}
}
