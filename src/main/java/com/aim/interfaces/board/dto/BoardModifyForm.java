package com.aim.interfaces.board.dto;

import com.aim.application.board.dto.BoardModifyCommand;
import com.aim.domain.board.enums.BoardType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BoardModifyForm {
	@NotNull(message = "{board.board-id.notblank}")
	private Long boardId;
	@NotBlank(message = "{board.title.notblank}")
	private String title;
	@NotBlank(message = "{board.contents.notblank}")
	private String contents;
	@Enumerated(EnumType.STRING)
	private BoardType type;
	
	public BoardModifyCommand toBoardModifyCommad() {
		return BoardModifyCommand.builder()
				.boardId(this.boardId)
				.title(this.title)
				.contents(this.contents)
				.type(this.type)
				.build();
	}
}
