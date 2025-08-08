package com.aim;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.game.dto.CircleDto;
import com.aim.application.game.dto.PvpMatchingDto;
import com.aim.application.game.dto.PvpMatchingMemberDto;
import com.aim.application.game.dto.ScoreDto;
import com.aim.application.game.service.PvpService;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.entity.Pvp;
import com.aim.domain.game.enums.MatchType;
import com.aim.domain.game.repository.GameRepository;
import com.aim.domain.game.repository.PvpRepository;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.repository.MemberRepository;

@SpringBootTest
@Transactional
public class PvpTest {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PvpRepository pvpRepository;
	@Autowired
	private PvpService pvpService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void gameStart() {
		List<PvpMatchingMemberDto> matchingUser = new ArrayList<PvpMatchingMemberDto>();
		Member member1 = memberRepository.findById((long)1).get();	
		Member member2 = memberRepository.findById((long)2).get();	
		PvpMatchingMemberDto memberDto1 = PvpMatchingMemberDto.from(member1,null);
		PvpMatchingMemberDto memberDto2 = PvpMatchingMemberDto.from(member2,null);
		matchingUser.add(memberDto1);
		matchingUser.add(memberDto2);
		
		Game game = gameRepository.findById((long)2).orElseThrow();
		Pvp pvp = new Pvp(game);
		pvpRepository.save(pvp);
		PvpMatchingDto pvpMatching = new PvpMatchingDto(pvp.getPvpId(), game, matchingUser, MatchType.NEWMATCH);
		
		pvpService.pvpSchedule(pvpMatching);
		
		redisTemplate.opsForValue().set("pvp:game:"+pvpMatching.getPvpId(), pvpMatching);
		System.out.println("pvpId:"+pvpMatching.getPvpId());
	}
	
	@Test
	public void findGame() {
		PvpMatchingDto pvpMatching = (PvpMatchingDto) redisTemplate.opsForValue().get("pvp:game:735");
		System.out.println("circleMap:"+pvpMatching.getCircleMap());
	}
	
	@Test
	public void redisTest() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		redisTemplate.opsForValue().set("test:1", list);
		list.add(2);
		redisTemplate.opsForValue().getAndSet("test:1", list);
	}
	
	@Test
	public void redisFindTest() {
		PvpMatchingDto pvpMatchingDto = (PvpMatchingDto) redisTemplate.opsForValue().get("pvp:game:794");
		
		List<PvpMatchingMemberDto> matchingUserList = pvpMatchingDto.getMatchingUser();
		
		for(PvpMatchingMemberDto matchingUser : matchingUserList) {
			System.out.println(matchingUser.getScore());
		}
	}
	
	@Test
	public void socreTest() {
		PvpMatchingDto pvpMatchingDto = (PvpMatchingDto) redisTemplate.opsForValue().get("pvp:game:800");
		
		List<PvpMatchingMemberDto> matchingUserList = pvpMatchingDto.getMatchingUser();
		
		for(PvpMatchingMemberDto matchingUser : matchingUserList) {
			if(matchingUser.getLoginId().equals("admin")) {
				ScoreDto score = matchingUser.getScore();
//				score.hit(10);
			}
			System.out.println(matchingUser.getScore());
		}
		
		redisTemplate.opsForValue().set("pvp:game:"+pvpMatchingDto.getPvpId(), pvpMatchingDto);
		

	}
	
	@Test
	public void clickTest() {
		CircleDto circle = new CircleDto();
		// 과녁 생성 시, Hash 구조로 저장
	}
}
