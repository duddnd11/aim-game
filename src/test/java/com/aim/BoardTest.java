package com.aim;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.Board;
import com.aim.domain.BoardType;
import com.aim.dto.BoardDto;
import com.aim.form.BoardForm;
import com.aim.repository.BoardRepository;
import com.aim.service.BoardService;

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
		BoardForm boardForm = new BoardForm();
		
		for(int i=2;i<100;i++) {
			boardForm.setTitle("공지테스트 제목"+i);
			boardForm.setContents("공지테스트 내용"+i);
			boardForm.setType(BoardType.NOTICE);
			boardService.boardWrite(boardForm,(long)13);
		}
	}
	
	@Test
	void 공지사항() {
		Page<Board> boardList = boardRepository.findByType(BoardType.NOTICE, PageRequest.of(0, 10));
		Page<BoardDto> boardDtoList = boardList.map(b -> new BoardDto(b));
		System.out.println("ddd:"+boardDtoList.getNumber());
	}
	
	@Test
	void 내가쓴글() {
		boardRepository.findByBoardIdAndMemberMemberId((long)2,(long)3);
	}
}
