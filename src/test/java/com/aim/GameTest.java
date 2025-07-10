package com.aim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.Game;
import com.aim.domain.GameMode;
import com.aim.domain.Member;
import com.aim.dto.GameDto;
import com.aim.dto.MatchType;
import com.aim.dto.PvpMatchingMemberDto;
import com.aim.form.GameForm;
import com.aim.repository.GameRepository;
import com.aim.repository.GameRepositoryCustom;
import com.aim.repository.MemberRepository;
import com.aim.repository.ScoreRepository;
import com.aim.service.GameService;
import com.aim.service.PvpService;

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
	GameRepositoryCustom gameRepositoryCustom;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	PvpService pvpService;
	
	@Autowired
	ScheduledExecutorService scheduler;
	
	@Test
//	@Rollback(value = false)
	void 게임생성() {
		Member member =memberRepository.findByLoginId("test").get();
		
		GameForm gameForm = new GameForm();
		gameForm.setGameName("Normal");
		gameForm.setEndHit(100);
		gameForm.setEndLoss(100);
		gameForm.setEndMiss(100);
		gameForm.setGameMode(GameMode.NORMAL);
		gameForm.setGameTime(100);
		gameForm.setHitPoint(10);
		gameForm.setLossPoint(10);
		gameForm.setMissPoint(10);
		gameForm.setMaxTargetSize(10);
		gameForm.setMinTargetSize(3);
		
		Game game = new Game(gameForm, member);
		
		gameRepository.save(game);
	}
	
	@Test
	void 메인게임리스트() {
		Long memberId = (long)3;
		List<GameDto> gameList = gameRepositoryCustom.findMainGame(memberId);
		for(GameDto g : gameList) {
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
		GameDto gameDto = new GameDto();
		gameDto.setGameId((long)2);
		List<PvpMatchingMemberDto> matchingUser = new ArrayList<PvpMatchingMemberDto>();
		pvpService.gameStart(gameDto, matchingUser, MatchType.NEWMATCH);
	}
	
	@Test
	void 테스트1() {
		List<PvpMatchingMemberDto> matchingUser = new ArrayList<PvpMatchingMemberDto>();;
		matchingUser.add(new PvpMatchingMemberDto());
		
		for(PvpMatchingMemberDto pvpMembers: matchingUser) {
			System.out.println("ddd");
		}
	}
}
