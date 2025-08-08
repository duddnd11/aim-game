package com.aim.interfaces.game.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aim.annotation.Auth;
import com.aim.application.game.dto.GameResult;
import com.aim.application.game.dto.ScoreResult;
import com.aim.application.game.service.GameService;
import com.aim.application.game.service.ScoreService;
import com.aim.domain.SliceDto;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.game.dto.GameForm;
import com.aim.interfaces.game.dto.GameModifyForm;
import com.aim.interfaces.game.dto.GameResponse;
import com.aim.interfaces.game.dto.ScoreResponse;

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
	public ResponseEntity<GameResponse> makeGame(GameForm gameForm, @Auth AuthUser user) {
		GameResult gameResult = gameService.makeGame(gameForm.toCommand(), user.getMemberId());
		return ResponseEntity.ok().body(GameResponse.from(gameResult));
	}
	
	/**
	 * 게임 정보 수정
	 * @param gameForm
	 * @param gameId
	 * @return
	 */
	@PostMapping("/modify/{gameId}")
	public ResponseEntity<GameResponse> gameModify(GameModifyForm gameModifyForm, @PathVariable(value = "gameId") Long gameId){
		GameResult gameResult = gameService.gameModify(gameModifyForm.toCommand());
		return ResponseEntity.ok().body(GameResponse.from(gameResult));
	}
	
	/**
	 * 게임 삭제
	 * @param gameId
	 * @param user
	 */
	@PatchMapping("/use")
	public ResponseEntity<Boolean> deleteGame(Long gameId, @Auth AuthUser user) {
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
	public ResponseEntity<SliceDto<ScoreResponse>> gameStat(Model model,@PathVariable(value="gameId") Long gameId, @PathVariable(value="page") int page, @Auth AuthUser user) {
		SliceDto<ScoreResult> scoreResults = scoreService.memberScoreStat(gameId, user.getMemberId(), page);
		SliceDto<ScoreResponse> scoreStats = scoreResults.map(s -> ScoreResponse.from(s));
		return ResponseEntity.ok().body(scoreStats);
	}
	
}
