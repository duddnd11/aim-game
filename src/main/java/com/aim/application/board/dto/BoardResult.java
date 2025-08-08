package com.aim.application.board.dto;

import com.aim.domain.BaseDto;
import com.aim.domain.board.entity.Board;
import com.aim.domain.board.enums.BoardType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BoardResult extends BaseDto{
	private Long boardId;
	
	private String nickname;
	
	private String title;
	
	private String contents;
	
	@Enumerated(EnumType.STRING)
	private BoardType type;

	public static BoardResult from(Board board) {
		return BoardResult.builder()
				.boardId(board.getBoardId())
				.nickname(board.getMember().getNickname())
				.title(board.getTitle())
				.contents(board.getContents())
				.type(board.getType())
				.createdDate(board.getCreatedDate())
				.modifiedDate(board.getModifiedDate())
				.build();
	}
}
