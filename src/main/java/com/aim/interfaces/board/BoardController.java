package com.aim.interfaces.board;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aim.annotation.Auth;
import com.aim.application.board.dto.BoardResult;
import com.aim.application.board.service.BoardService;
import com.aim.domain.board.enums.BoardType;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.board.dto.BoardForm;
import com.aim.interfaces.board.dto.BoardModifyForm;
import com.aim.interfaces.board.dto.BoardViewModel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;
	
	/**
	 * 게시판 리스트
	 * @param model
	 * @param type
	 * @param page
	 * @return
	 */
	@GetMapping("/{type}/{page}")
	public String board(Model model, @PathVariable(value = "type") BoardType type, @PathVariable(value="page") int page, @Auth AuthUser user) {
		Page<BoardResult> boardList = boardService.board(type,page);
		Page<BoardViewModel> boardViewModelList = boardList.map(b -> BoardViewModel.from(b));
		model.addAttribute("boardList", boardViewModelList);
		return "board/board";
	}
	
	/**
	 * 게시글 작성 페이지
	 * @param type
	 * @param model
	 * @return
	 */
	@GetMapping("/write/{type}")
	public String boardWrite(@PathVariable(value="type") BoardType type, Model model, @Auth AuthUser user) {
		return "board/board-write";
	}
	
	/**
	 * 게시글 작성
	 * @param boardForm
	 * @param user
	 * @return
	 */
	@PostMapping("")
	public String boardWriteAction(@Valid BoardForm boardForm, @Auth AuthUser user) {
		BoardResult board = boardService.boardWrite(boardForm.toBoardCommand(), user.getMemberId());
		return "redirect:/board/detail/"+board.getBoardId();
	}
	
	/**
	 * 게시글 보기
	 * @param boardId
	 * @param model
	 * @return
	 */
	@GetMapping("/detail/{boardId}")
	public String boardDetail(@PathVariable(value = "boardId")Long boardId, Model model, @Auth AuthUser user) {
		BoardResult boardResult = boardService.boardDetail(boardId);
		model.addAttribute("board", BoardViewModel.from(boardResult));
		return "board/board-detail";
	}
	
	/**
	 * 게시글 수정 페이지
	 * @param boardId
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/modify/{boardId}")
	public String boardModify(@PathVariable(value="boardId")Long boardId, Model model,@Auth AuthUser user) {
		BoardResult boardResult = boardService.boardDetail(boardId);
		model.addAttribute("board", BoardViewModel.from(boardResult));
		return "board/board-modify";
	}
	
	/**
	 * 게시글 수정
	 * @param boardModifyForm
	 * @param user
	 * @return
	 */
	@PostMapping("/modify")
	public String boardModifyAction(@Valid BoardModifyForm boardModifyForm, @Auth AuthUser user) {
		BoardResult boardResult = boardService.boardModify(boardModifyForm.toBoardModifyCommad(), user.getMemberId());
		return "redirect:/board/detail/"+boardResult.getBoardId();
	}
}
