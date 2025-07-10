package com.aim.form;

import com.aim.domain.BoardType;

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
