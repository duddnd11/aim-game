package com.aim;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.SliceDto;
import com.aim.application.game.dto.ScoreResult;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.entity.Pvp;
import com.aim.domain.game.entity.Score;
import com.aim.domain.game.repository.GameRepository;
import com.aim.domain.game.repository.PvpRepository;
import com.aim.domain.game.repository.ScoreRepository;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;
import com.aim.interfaces.game.dto.ScoreForm;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Rollback
@Transactional
@Slf4j
public class ScoreTest {

	@Autowired
	ScoreRepository scoreRepository;
	@Autowired
	GameRepository gameRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	PvpRepository pvpRepository;
	
	@Test
	@Rollback(value = false)
	void 스코어저장() {
		ScoreForm scoreForm = new ScoreForm();
		scoreForm.setTotalScore(100);
		scoreForm.setHit(1);
		scoreForm.setHitScore(100);
		
		Game game = gameRepository.findById((long)2).get();
		Member member = memberRepository.findById((long)2).get();
		
		for(int i=0;i<99;i++) {
			Score score = new Score(scoreForm,member,game);
			Score scoreData = scoreRepository.save(score);
		}
	}
	
	@Test
	void 스코어저장2() {
		Optional<Pvp> pvpOp = pvpRepository.findById((long)0);
	}
	
	@Test
	void 스코어리스트() {
		log.info("ddd");
		Score score = scoreRepository.findById((long) 12).get();
		Game game =score.getGame();
		Member member =score.getMember();
	}
	
	@Test
	void 랭킹() {
		Slice<Score> scoreList = scoreRepository.findByGameId((long)2,PageRequest.of(0, 10));
		if(scoreList.hasNext()) {
			Slice<Score> scoreList2 = scoreRepository.findByGameId((long)2,scoreList.nextPageable());
		}
	}
	
	@Test
	void 랭킹2() {
		Long gameId = (long)2;
		Long memberId = (long)2;
		SliceDto<ScoreResult> scoreList = scoreRepository.findScoreStat(gameId, memberId,1);
		System.out.println("score:"+scoreList);
	}
	
}
