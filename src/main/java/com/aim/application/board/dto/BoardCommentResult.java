package com.aim.application.board.dto;

import com.aim.domain.BaseDto;
import com.aim.domain.board.entity.BoardComment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BoardCommentResult extends BaseDto{
	private Long boardCommentId;
	
	private String nickname;
	
	private BoardResult board;
	
	private String contents;
	
	public static BoardCommentResult from(BoardComment boardComment) {
		return BoardCommentResult.builder()
				.boardCommentId(boardComment.getBoardCommentId())
				.nickname(boardComment.getMember().getNickname())
				.board(BoardResult.from(boardComment.getBoard()))
				.contents(boardComment.getContents())
				.createdDate(boardComment.getCreatedDate())
				.modifiedDate(boardComment.getModifiedDate())
				.build();
	}
}
