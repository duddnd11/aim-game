package com.aim.application.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.board.dto.BoardCommand;
import com.aim.application.board.dto.BoardModifyCommand;
import com.aim.application.board.dto.BoardResult;
import com.aim.domain.board.entity.Board;
import com.aim.domain.board.enums.BoardType;
import com.aim.domain.board.repository.BoardRepository;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;

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
	public Page<BoardResult> board(BoardType type, int page){
		Page<Board> boardList = boardRepository.findByType(type, PageRequest.of(page-1, 10));
		
		Page<BoardResult> boardDtoList = boardList.map(b -> BoardResult.from(b));
		
		return boardDtoList;
	}
	
	/**
	 * 게시글 선택
	 * @param boardId
	 * @return
	 */
	public BoardResult boardDetail(Long boardId) {
		Board board = boardRepository.findById(boardId).orElseThrow();
		BoardResult boardDto = BoardResult.from(board);
		return boardDto;
	}
	
	/**
	 * 게시글 작성
	 * @param boardForm
	 * @param memberId
	 * @return
	 */
	@Transactional
	public BoardResult boardWrite(BoardCommand boardCommand, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow();
		Board board = new Board(boardCommand.getTitle(), boardCommand.getContents(), boardCommand.getType(), member);
		
		return BoardResult.from(boardRepository.save(board));
	}
	
	/**
	 * 게시글 수정
	 * @param boardForm
	 * @param memberId
	 * @return
	 */
	@Transactional
	public BoardResult boardModify(BoardModifyCommand boardModifyCommand, Long memberId) {
		Board board = boardRepository.findByBoardIdAndMemberMemberId(boardModifyCommand.getBoardId(), memberId).orElseThrow();
		board.modify(boardModifyCommand.getTitle(), boardModifyCommand.getContents(), boardModifyCommand.getType());
		
		return BoardResult.from(board);
	}

}
