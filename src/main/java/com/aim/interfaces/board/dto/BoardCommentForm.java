package com.aim.interfaces.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardCommentForm {
	
	private Long boardId;
	
	@NotBlank(message = "{board.contents.notblank}")
	private String contents;
}
