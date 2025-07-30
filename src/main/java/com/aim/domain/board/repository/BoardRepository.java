package com.aim.domain.board.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aim.domain.board.entity.Board;
import com.aim.domain.board.enums.BoardType;

public interface BoardRepository {
	Board save(Board board);
	
	Optional<Board> findById(Long boardId);
	
	Optional<Board> findByBoardIdAndMemberMemberId(Long boardId, Long memberId);
	
	Page<Board> findByType(BoardType type, Pageable pageable);
}
