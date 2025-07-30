package com.aim.application.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.board.dto.BoardCommentDto;
import com.aim.domain.board.entity.Board;
import com.aim.domain.board.entity.BoardComment;
import com.aim.domain.board.repository.BoardCommentRepository;
import com.aim.domain.board.repository.BoardRepository;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;
import com.aim.interfaces.board.dto.BoardCommentForm;

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
