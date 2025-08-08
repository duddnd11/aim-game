package com.aim.interfaces.game.restcontroller;

import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aim.annotation.Auth;
import com.aim.application.game.dto.ScoreResult;
import com.aim.application.game.service.ScoreService;
import com.aim.domain.member.entity.AuthUser;
import com.aim.exception.NotAuthException;
import com.aim.interfaces.game.dto.ScoreForm;
import com.aim.interfaces.game.dto.ScoreResponse;

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
	public ResponseEntity<ScoreResponse> saveScore(ScoreForm scoreForm, @Auth AuthUser user) {
		log.info("scoreForm:"+scoreForm);
		
		try {
			ScoreResult scoreResult = scoreService.saveScore(scoreForm, user.getMemberId(),null);
			return ResponseEntity.ok().body(ScoreResponse.from(scoreResult));
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
	public ResponseEntity<Slice<ScoreResponse>> gameRanking(@PathVariable(value = "gameId") Long gameId, @PathVariable(value="page") int page){
		log.info("gameRanking");
		Slice<ScoreResult> scoreResults = scoreService.scoreRanking(gameId, page);
		Slice<ScoreResponse> scoreResponses = scoreResults.map(s -> ScoreResponse.from(s));
		return ResponseEntity.ok().body(scoreResponses);
	}
}
