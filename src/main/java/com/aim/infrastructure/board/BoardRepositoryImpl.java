package com.aim.infrastructure.board;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aim.domain.board.entity.Board;
import com.aim.domain.board.enums.BoardType;
import com.aim.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{
	private final BoardJpaRepository boardJpaRepository;

	@Override
	public Board save(Board board) {
		return boardJpaRepository.save(board);
	}

	@Override
	public Optional<Board> findById(Long boardId) {
		return boardJpaRepository.findById(boardId);
	}

	@Override
	public Optional<Board> findByBoardIdAndMemberMemberId(Long boardId, Long memberId) {
		return boardJpaRepository.findByBoardIdAndMemberMemberId(boardId, memberId);
	}

	@Override
	public Page<Board> findByType(BoardType type, Pageable pageable) {
		return boardJpaRepository.findByType(type, pageable);
	}
}
