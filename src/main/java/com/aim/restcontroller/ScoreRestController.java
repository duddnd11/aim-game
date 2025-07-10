package com.aim.restcontroller;

import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aim.annotation.Auth;
import com.aim.domain.AuthUser;
import com.aim.dto.ScoreDto;
import com.aim.exception.NotAuthException;
import com.aim.form.ScoreForm;
import com.aim.service.ScoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/score")
public class ScoreRestController {
	
	private final ScoreService scoreService;
	
	/**
	 * 게임 스코어 저장
	 * @param scoreForm
	 * @param user
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<ScoreDto> saveScore(ScoreForm scoreForm, @Auth AuthUser user) {
		log.info("scoreForm:"+scoreForm);
		
		try {
			ScoreDto scoreDto = scoreService.saveScore(scoreForm, user.getMemberId(),null);
			return ResponseEntity.ok().body(scoreDto);
		}catch (NullPointerException e) {
			throw new NotAuthException("로그인 정보 없음");
		}
	}
	
	/**
	 * 게임 랭킹
	 * @param gameId
	 * @param page
	 * @return
	 */
	@GetMapping("/ranking/{gameId}/{page}")
	public ResponseEntity<Slice<ScoreDto>> gameRanking(@PathVariable(value = "gameId") Long gameId, @PathVariable(value="page") int page){
		log.info("gameRanking");
		Slice<ScoreDto> scores = scoreService.scoreRanking(gameId, page);
		return ResponseEntity.ok().body(scores);
	}
}
