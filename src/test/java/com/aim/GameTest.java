package com.aim;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.game.dto.GameResult;
import com.aim.application.game.service.GameService;
import com.aim.application.game.service.PvpService;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.enums.GameMode;
import com.aim.domain.game.repository.GameRepository;
import com.aim.domain.game.repository.ScoreRepository;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;

@SpringBootTest
@Rollback
@Transactional
public class GameTest {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	GameRepository gameRepository;
	
	@Autowired
	ScoreRepository scoreRepository;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	PvpService pvpService;
	
	@Autowired
	ScheduledExecutorService scheduler;
	
	@Test
//	@Rollback(value = false)
	void 게임생성() {
		Member member = memberRepository.findByLoginId("test").get();
		
		String gameName = "Normal";
		int endHit = 100;
		int endLoss = 100;
		int endMiss = 100;
		GameMode gameMode = GameMode.NORMAL;
		int gameTime = 100;
		int hitPoint = 10;
		int lossPoint = 10;
		int missPoint = 10;
		int maxTargetSize = 10;
		int minTargetSize = 3;
		
		Game game = new Game(gameName, gameMode, gameTime, endHit, endMiss, endLoss, minTargetSize, maxTargetSize, hitPoint, missPoint,
				lossPoint, hitPoint, lossPoint, missPoint, minTargetSize, maxTargetSize, minTargetSize, member);
		
		gameRepository.save(game);
	}
	
	@Test
	void 메인게임리스트() {
		Long memberId = (long)3;
		List<GameResult> gameList = gameRepository.findMainGame(memberId);
		for(GameResult g : gameList) {
			System.out.println(g);
		}
	}
	
	@Test
	void 테스트() {
		Integer firstObj = 1;
		Integer secondObj = 1;
	    boolean valid = firstObj == null && secondObj == null || firstObj != null && firstObj.compareTo(secondObj) <= 0;
	    System.out.println("valid:"+valid);
	}
	
	@Test
	void pvpGameStart() {
//		GameDto gameDto = GameDto.from(null);
//		gameDto.setGameId((long)2);
//		List<PvpMatchingMemberDto> matchingUser = new ArrayList<PvpMatchingMemberDto>();
//		pvpService.gameStart(gameDto, matchingUser, MatchType.NEWMATCH);
	}
	
	@Test
	void 테스트1() {
//		List<PvpMatchingMemberDto> matchingUser = new ArrayList<PvpMatchingMemberDto>();;
//		matchingUser.add(new PvpMatchingMemberDto());
//		
//		for(PvpMatchingMemberDto pvpMembers: matchingUser) {
//			System.out.println("ddd");
//		}
	}
}
