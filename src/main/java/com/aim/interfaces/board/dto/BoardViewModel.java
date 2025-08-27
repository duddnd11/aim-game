package com.aim.interfaces.board.dto;

import java.time.LocalDateTime;

import com.aim.application.board.dto.BoardResult;
import com.aim.domain.board.enums.BoardType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardViewModel {
	private Long boardId;
	
	private String nickname;
	
	private String title;
	
	private String contents;
	
	@Enumerated(EnumType.STRING)
	private BoardType type;
	
	private LocalDateTime createdDate;
	
	public static BoardViewModel from(BoardResult boardResult) {
		return BoardViewModel.builder()
				.boardId(boardResult.getBoardId())
				.nickname(boardResult.getNickname())
				.title(boardResult.getTitle())
				.contents(boardResult.getContents())
				.type(boardResult.getType())
				.createdDate(boardResult.getCreatedDate())
				.build();
	}
}
