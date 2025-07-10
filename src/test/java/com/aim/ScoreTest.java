package com.aim;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.Game;
import com.aim.domain.Member;
import com.aim.domain.Pvp;
import com.aim.domain.Score;
import com.aim.dto.ScoreDto;
import com.aim.dto.SliceDto;
import com.aim.form.ScoreForm;
import com.aim.repository.GameRepository;
import com.aim.repository.MemberRepository;
import com.aim.repository.PvpRepository;
import com.aim.repository.ScoreRepository;
import com.aim.repository.ScoreRepositoryCustom;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Rollback
@Transactional
@Slf4j
public class ScoreTest {

	@Autowired
	ScoreRepository scoreRepository;
	@Autowired
	ScoreRepositoryCustom scoreRepositoryCustom;
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
		SliceDto<ScoreDto> scoreList = scoreRepositoryCustom.findScoreStat(gameId, memberId,1);
		System.out.println("score:"+scoreList);
	}
	
}
