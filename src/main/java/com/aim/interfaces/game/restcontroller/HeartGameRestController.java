package com.aim.interfaces.game.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aim.annotation.Auth;
import com.aim.application.game.service.HeartGameService;
import com.aim.domain.member.entity.AuthUser;
import com.aim.interfaces.game.dto.HeartGameResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/heart")
public class HeartGameRestController {
	
	private final HeartGameService heartGameService;
	
	@PutMapping("/{gameId}")
	public ResponseEntity<HeartGameResponse> heartGame(@PathVariable(value = "gameId") Long gameId, @Auth AuthUser user){
		log.info("gameId:"+gameId);
		
		return ResponseEntity.ok().body(HeartGameResponse.from(heartGameService.heartGame(gameId, user.getMemberId())));
	}
}
