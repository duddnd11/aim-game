package com.aim.dto;

import com.aim.domain.Board;
import com.aim.domain.BoardType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BoardDto extends BaseDto{
	private Long boardId;
	
	private MemberDto member;
	
	private String title;
	
	private String contents;
	
	@Enumerated(EnumType.STRING)
	private BoardType type;
	
	public BoardDto() {}
	
	public BoardDto(Board board) {
		this.boardId=board.getBoardId();
		this.title=board.getTitle();
		this.contents=board.getContents();
		this.type=board.getType();
		this.member=new MemberDto(board.getMember());
		this.setCreatedDate(board.getCreatedDate());
		this.setModifiedDate(board.getModifiedDate());
	}
}
