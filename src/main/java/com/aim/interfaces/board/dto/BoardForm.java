package com.aim.interfaces.board.dto;

import com.aim.domain.board.enums.BoardType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardForm {
	@NotBlank(message = "{board.title.notblank}")
	private String title;
	@NotBlank(message = "{board.contents.notblank}")
	private String contents;
	@Enumerated(EnumType.STRING)
	private BoardType type;
}
