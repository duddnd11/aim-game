package com.aim.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.Board;
import com.aim.domain.BoardComment;
import com.aim.domain.Member;
import com.aim.dto.BoardCommentDto;
import com.aim.form.BoardCommentForm;
import com.aim.repository.BoardCommentRepository;
import com.aim.repository.BoardRepository;
import com.aim.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardCommentService {
	private final BoardCommentRepository boardCommentRepository;
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	
	/**
	 * 게시글 댓글 목록
	 * @param boardId
	 * @return
	 */
	public List<BoardCommentDto> boardComment(Long boardId){
		List<BoardComment> boardCommentList = boardCommentRepository.findByBoardBoardId(boardId);
		List<BoardCommentDto> boardCommentDtoList = boardCommentList.stream()
				.map(b -> new BoardCommentDto(b))
				.collect(Collectors.toList());
		
		return boardCommentDtoList;
	}
	
	/**
	 * 댓글 작성
	 * @param boardCommentForm
	 * @param memberId
	 * @return
	 */
	@Transactional
	public BoardCommentDto boardCommentWrite(BoardCommentForm boardCommentForm, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		Board board = boardRepository.findById(boardCommentForm.getBoardId()).orElseThrow();
		BoardComment boardComment = new BoardComment(boardCommentForm, member, board);
		
		return new BoardCommentDto(boardCommentRepository.save(boardComment));
	}
	
}
