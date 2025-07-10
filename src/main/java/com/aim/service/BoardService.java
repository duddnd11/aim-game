package com.aim.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.Board;
import com.aim.domain.BoardType;
import com.aim.domain.Member;
import com.aim.dto.BoardDto;
import com.aim.form.BoardForm;
import com.aim.form.BoardModifyForm;
import com.aim.repository.BoardRepository;
import com.aim.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	
	/**
	 * 타입별 게시글 목록
	 * @param type
	 * @return
	 */
	public Page<BoardDto> board(BoardType type, int page){
		Page<Board> boardList = boardRepository.findByType(type, PageRequest.of(page-1, 10));
		
		Page<BoardDto> boardDtoList = boardList.map(b -> new BoardDto(b));
		
		return boardDtoList;
	}
	
	/**
	 * 게시글 선택
	 * @param boardId
	 * @return
	 */
	public BoardDto boardDetail(Long boardId) {
		Board board = boardRepository.findById(boardId).orElseThrow();
		BoardDto boardDto = new BoardDto(board);
		return boardDto;
	}
	
	/**
	 * 게시글 작성
	 * @param boardForm
	 * @param memberId
	 * @return
	 */
	@Transactional
	public BoardDto boardWrite(BoardForm boardForm, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		Board board = new Board(boardForm, member);
		
		return new BoardDto(boardRepository.save(board));
	}
	
	/**
	 * 게시글 수정
	 * @param boardForm
	 * @param memberId
	 * @return
	 */
	@Transactional
	public BoardDto boardModify(BoardModifyForm boardModifyForm, Long memberId) {
		Board board = boardRepository.findByBoardIdAndMemberMemberId(boardModifyForm.getBoardId(), memberId).orElseThrow();
		board.modify(boardModifyForm);
		
		return new BoardDto(board);
	}

}
