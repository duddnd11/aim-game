package com.aim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.BoardComment;

public interface BoardCommentRepository extends JpaRepository<BoardComment,Long>{
	List<BoardComment> findByBoardBoardId(Long boardId);
}
