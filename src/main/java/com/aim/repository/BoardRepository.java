package com.aim.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aim.domain.Board;
import com.aim.domain.BoardType;

public interface BoardRepository extends JpaRepository<Board,Long>{
	
	@Query("select b from Board b join fetch b.member where b.type=:type order by b.boardId desc")
	Page<Board> findByType(@Param("type") BoardType type, Pageable pageable);
	
	@Query("select b from Board b join fetch b.member where b.boardId=:boardId")
	Optional<Board> findById(@Param("boardId")Long boardId);
	
	Optional<Board> findByBoardIdAndMemberMemberId(Long boardId, Long memberId);

}
