package com.aim.interfaces.game;

import java.util.List;

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
import com.aim.application.game.GameService;
import com.aim.application.game.ScoreService;
import com.aim.domain.SliceDto;
import com.aim.domain.game.dto.GameDto;
import com.aim.domain.game.dto.ScoreDto;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.game.dto.GameForm;
import com.aim.interfaces.game.dto.GameModifyForm;

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
		GameDto gameDto = gameService.selectGame(gameId);
		model.addAttribute("game", gameDto);
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
		gameService.makeGame(gameForm, user.getMemberId());
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
		GameDto game = gameService.selectGame(gameId);
		
		model.addAttribute("game", game);
		
		Slice<ScoreDto> scoreList = scoreService.scoreRanking(gameId, page);
		model.addAttribute("scoreList", scoreList);
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
		GameDto gameDto = gameService.selectGame(gameId, user.getMemberId());
		model.addAttribute("game", new GameModifyForm(gameDto));
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
		
		gameService.gameModify(gameModifyForm);
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
		SliceDto<ScoreDto> scoreStats = scoreService.memberScoreStat(gameId, user.getMemberId(), 0);
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
		List<GameDto> gameList =  gameService.findByUser(user.getMemberId());
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
		List<GameDto> gameList =  gameService.findByHeart(user.getMemberId());
		model.addAttribute("gameList", gameList);
		return "game/heart";
	}
}
