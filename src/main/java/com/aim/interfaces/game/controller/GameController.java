package com.aim.interfaces.game.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aim.annotation.Auth;
import com.aim.application.SliceDto;
import com.aim.application.game.dto.GameResult;
import com.aim.application.game.dto.ScoreResult;
import com.aim.application.game.service.GameService;
import com.aim.application.game.service.ScoreService;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.game.dto.GameForm;
import com.aim.interfaces.game.dto.GameModifyForm;
import com.aim.interfaces.game.dto.GameResponse;
import com.aim.interfaces.game.dto.ScoreResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("game")
@RequiredArgsConstructor
public class GameController {

	private final GameService gameService;
	private final ScoreService scoreService;
	
	/**
	 * 게임 페이지
	 * @param gameId
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/{gameId}")
	public String selectGame(@PathVariable(value = "gameId") Long gameId, Model model, @Auth AuthUser user) {
		log.info("selectGame");
		GameResult gameResult = gameService.selectGame(gameId);
		model.addAttribute("game", GameResponse.from(gameResult));
		return "game/game";
	}
	
	/**
	 * 게임 등록 페이지
	 * @param user
	 * @return
	 */
	@GetMapping("")
	public String makeGame(@Auth AuthUser user, @ModelAttribute("game") GameForm gameForm) {
		log.info("makeGame");
		return "game/game-make";
	}
	
	/**
	 * 게임 등록
	 * @param gameForm
	 * @param user
	 * @return
	 */
	@PostMapping("")
	public String makeGameAction(@Validated @ModelAttribute("game") GameForm gameForm, BindingResult bindingResult, @Auth AuthUser user) {
		if (bindingResult.hasErrors()) {
			log.info("errors={}", bindingResult);
			return "game/game-make";
		}
		gameService.makeGame(gameForm.toCommand(), user.getMemberId());
		return "redirect:/";
	}
	
	/**
	 * 게임별 랭킹
	 * @param model
	 * @param gameMode
	 * @param page
	 * @return
	 */
	@GetMapping("/ranking/{gameId}/{page}")
	public String gameRanking(Model model,@PathVariable(value="gameId") Long gameId, @PathVariable(value="page") int page, @Auth AuthUser user) {
		GameResult game = gameService.selectGame(gameId);
		
		model.addAttribute("game", game);
		
		Slice<ScoreResult> scoreList = scoreService.scoreRanking(gameId, page);
		Slice<ScoreResponse> scoreResponses = scoreList.map(s -> ScoreResponse.from(s));
		model.addAttribute("scoreList", scoreResponses);
		return "game/game-ranking";
	}
	
	/**
	 * 게임 수정 페이지
	 * @param model
	 * @param user
	 * @param gameId
	 * @return
	 */
	@GetMapping("/modify/{gameId}")
	public String modifyGame(Model model,@Auth AuthUser user, @PathVariable(value="gameId") Long gameId) {
		GameResult gameResult = gameService.selectGame(gameId, user.getMemberId());
		model.addAttribute("game", new GameModifyForm(gameResult));
		return "game/game-modify";
	}
	
	/**
	 * 게임 정보 수정
	 * @param gameModifyForm
	 * @param user
	 * @param gameId
	 * @return
	 */
	@PostMapping("/modify/{gameId}")
	public String modifyGameAction(@Validated @ModelAttribute("game") GameModifyForm gameModifyForm, BindingResult bindingResult, @Auth AuthUser user, @PathVariable(value="gameId") Long gameId) {
		log.info("modifyGameAction");
		if (bindingResult.hasErrors()) {
			log.info("errors={}", bindingResult);
			return "game/game-modify";
		}
		
		gameService.gameModify(gameModifyForm.toCommand());
		return "redirect:/game/member";
	}
	
	/**
	 * 게임 멤버별 통계
	 * @param gameId
	 * @param user
	 * @return
	 */
	@GetMapping("/stat/{gameId}")
	public String gameStat(Model model,@PathVariable(value="gameId") Long gameId, @Auth AuthUser user) {
		SliceDto<ScoreResult> scoreResults = scoreService.memberScoreStat(gameId, user.getMemberId(), 0);
		SliceDto<ScoreResponse> scoreStats = scoreResults.map(s -> ScoreResponse.from(s));
		model.addAttribute("gameId", gameId);
		model.addAttribute("scoreStats", scoreStats);
		return "game/game-stat";
	}
	
	/**
	 * 사용자 생성 게임 목록
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/member")
	public String userGame(Model model, @Auth AuthUser user) {
		List<GameResult> gameResultList =  gameService.findByUser(user.getMemberId());
		List<GameResponse> gameList = gameResultList.stream().map(g -> GameResponse.from(g)).collect(Collectors.toList());
		model.addAttribute("gameList", gameList);
		return "game/my-game";
	}
	
	/**
	 * 즐겨찾기
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/heart")
	public String heartGame(Model model, @Auth AuthUser user) {
		List<GameResult> gameResultList =  gameService.findByHeart(user.getMemberId());
		List<GameResponse> gameList = gameResultList.stream().map(g -> GameResponse.from(g)).collect(Collectors.toList());
		model.addAttribute("gameList", gameList);
		return "game/heart";
	}
}
