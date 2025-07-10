package com.aim.restcontroller;

import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aim.annotation.Auth;
import com.aim.domain.AuthUser;
import com.aim.dto.GameDto;
import com.aim.dto.ScoreDto;
import com.aim.dto.SliceDto;
import com.aim.form.GameForm;
import com.aim.form.GameModifyForm;
import com.aim.service.GameService;
import com.aim.service.ScoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/game")
public class GameRestController {

	private final GameService gameService;
	private final ScoreService scoreService;
	
	/**
	 * 게임 생성
	 * @param gameForm
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<GameDto> makeGame(GameForm gameForm, @Auth AuthUser user) {
		GameDto gameDto = gameService.makeGame(gameForm, user.getMemberId());
		return ResponseEntity.ok().body(gameDto);
	}
	
	/**
	 * 게임 정보 수정
	 * @param gameForm
	 * @param gameId
	 * @return
	 */
	@PostMapping("/modify/{gameId}")
	public ResponseEntity<GameDto> gameModify(GameModifyForm gameModifyForm, @PathVariable(value = "gameId") Long gameId){
		GameDto gameDto = gameService.gameModify(gameModifyForm);
		return ResponseEntity.ok().body(gameDto);
	}
	
	/**
	 * 게임 삭제
	 * @param gameId
	 * @param user
	 */
	@PatchMapping("/use")
	public ResponseEntity<Object> deleteGame(Long gameId, @Auth AuthUser user) {
		gameService.deleteGame(gameId, user.getMemberId());
		return ResponseEntity.ok().body(true);
	}
	
	/**
	 * 게임 통계
	 * @param model
	 * @param gameId
	 * @param page
	 * @param user
	 * @return
	 */
	@GetMapping("/stat/{gameId}/{page}")
	public ResponseEntity<SliceDto<ScoreDto>> gameStat(Model model,@PathVariable(value="gameId") Long gameId, @PathVariable(value="page") int page, @Auth AuthUser user) {
		SliceDto<ScoreDto> scoreStats = scoreService.memberScoreStat(gameId, user.getMemberId(), page);
		return ResponseEntity.ok().body(scoreStats);
	}
	
}
