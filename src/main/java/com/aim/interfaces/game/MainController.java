package com.aim.interfaces.game;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aim.annotation.Auth;
import com.aim.application.game.GameService;
import com.aim.domain.game.dto.GameDto;
import com.aim.domain.member.entity.AuthUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final GameService gameService;

	/**
	 * 게임 메인 페이지
	 * @param model
	 * @param user
	 * @return
	 */
	@GetMapping("/")
	public String main(Model model,@Auth AuthUser user) {
		log.info("main");
		List<GameDto> gameList = gameService.findMainGame(user);
		model.addAttribute("gameList", gameList);

		return "main";
	}

}
