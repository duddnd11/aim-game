package com.aim.application.board.dto;

import com.aim.domain.board.enums.BoardType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardModifyCommand {
	private Long boardId;
	private String title;
	private String contents;
	private BoardType type;
}
