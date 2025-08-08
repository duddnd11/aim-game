package com.aim.interfaces.board.dto;

import com.aim.application.board.dto.BoardCommand;
import com.aim.domain.board.enums.BoardType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardForm {
	@NotBlank(message = "{board.title.notblank}")
	private String title;
	@NotBlank(message = "{board.contents.notblank}")
	private String contents;
	@Enumerated(EnumType.STRING)
	private BoardType type;
	
	public BoardCommand toBoardCommand() {
		return BoardCommand.builder()
				.title(this.title)
				.contents(this.contents)
				.type(this.type)
				.build();
	}
}
