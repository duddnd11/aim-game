package com.aim.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aim.annotation.Auth;
import com.aim.domain.AuthUser;
import com.aim.service.HeartGameService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/heart")

public class HeartGameRestController {
	
	private final HeartGameService heartGameService;
	
	@PutMapping("/{gameId}")
	public ResponseEntity<Object> heartGame(@PathVariable(value = "gameId") Long gameId, @Auth AuthUser user){
		log.info("gameId:"+gameId);
		
		return ResponseEntity.ok().body(heartGameService.heartGame(gameId, user.getMemberId()));
	}
}
