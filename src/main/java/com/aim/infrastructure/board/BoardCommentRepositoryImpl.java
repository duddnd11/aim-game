package com.aim.infrastructure.board;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aim.domain.board.entity.BoardComment;
import com.aim.domain.board.repository.BoardCommentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardCommentRepositoryImpl implements BoardCommentRepository{
	private final BoardCommentJpaRepository boardCommentJpaRepository;
	
	@Override
	public List<BoardComment> findByBoardBoardId(Long boardId) {
		return boardCommentJpaRepository.findByBoardBoardId(boardId);
	}

	@Override
	public BoardComment save(BoardComment boardComment) {
		return boardCommentJpaRepository.save(boardComment);
	}

}
