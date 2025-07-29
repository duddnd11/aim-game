package com.aim.domain.board.enums;

public enum BoardType {
	NOTICE("공지사항"),
	FREE("자유 게시판");
	
	private final String boardTypeName;

	BoardType(String boardTypeName) {
		this.boardTypeName = boardTypeName;
	}

	public String getBoardTypeName() {
		return boardTypeName;
	}
	
}
