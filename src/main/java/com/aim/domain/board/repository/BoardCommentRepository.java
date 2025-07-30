package com.aim.domain.board.repository;

import java.util.List;

import com.aim.domain.board.entity.BoardComment;

public interface BoardCommentRepository {
	List<BoardComment> findByBoardBoardId(Long boardId);
	
	BoardComment save(BoardComment boardComment);
}
