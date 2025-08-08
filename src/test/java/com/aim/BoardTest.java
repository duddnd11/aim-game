package com.aim;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.board.dto.BoardResult;
import com.aim.application.board.service.BoardService;
import com.aim.domain.board.entity.Board;
import com.aim.domain.board.enums.BoardType;
import com.aim.domain.board.repository.BoardRepository;
import com.aim.interfaces.board.dto.BoardForm;

@SpringBootTest
@Rollback
@Transactional
public class BoardTest {

	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Test
	@Rollback(value = false)
	void 공지사항입력() {
		for(int i=2;i<100;i++) {
			BoardForm boardForm = BoardForm.builder()
					.title("공지테스트 제목"+i)
					.contents("공지테스트 내용"+i)
					.type(BoardType.NOTICE)
					.build();
			boardService.boardWrite(boardForm.toBoardCommand(),(long)13);
		}
	}
	
	@Test
	void 공지사항() {
		Page<Board> boardList = boardRepository.findByType(BoardType.NOTICE, PageRequest.of(0, 10));
		Page<BoardResult> boardDtoList = boardList.map(b -> BoardResult.from(b));
		System.out.println("ddd:"+boardDtoList.getNumber());
	}
	
	@Test
	void 내가쓴글() {
		boardRepository.findByBoardIdAndMemberMemberId((long)2,(long)3);
	}
}
