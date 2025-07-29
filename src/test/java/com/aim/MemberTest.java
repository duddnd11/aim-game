package com.aim;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.member.MemberService;
import com.aim.domain.member.dto.MemberDto;
import com.aim.domain.member.enums.MemberRole;
import com.aim.infrastructure.member.MemberRepository;
import com.aim.infrastructure.member.MemberRepositoryCustom;
import com.aim.interfaces.member.dto.MemberForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Rollback
@Transactional
public class MemberTest {
	
	@Autowired
	MemberService memberService;

	@Autowired 
	AuthenticationManagerBuilder authenticationManagerBuilder;

	@Autowired
	MemberRepositoryCustom memberRepositoryCustom;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
//	@Rollback(value=false)
	void 회원가입(){
		for(int i=5;i<=20;i++) {
		MemberForm memberForm = MemberForm.builder()
				.loginId("test"+i)
				.password("1234")
				.nickname("테스트"+i)
				.email("test@test"+i+".com")
				.role(MemberRole.ROLE_USER)
				.build();
		
			memberService.joinMember(memberForm);
		}
	}
	
	@Test
	void 로그인() {
//		memberService.login("test2", "1234");
	}
	
	@Test
	void 회원목록() {
		memberService.findMemberAll();
	}
	
//	@Test
	void 인증테스트() {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("test2", "1234");
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		User user = new User("test2","1234",authentication.getAuthorities());
		log.info("authenticationToken:"+authenticationToken);
		log.info("user:"+user);
		
	}
	
	@Test
	void 랭크() {
		int ranking = memberRepositoryCustom.memberRatingRank((long)2);
		System.out.println(ranking);
	}
	
	@Test
	void 랭크리스트() {
		List<MemberDto> list = memberRepositoryCustom.memberRatingRankList(16);
		for(MemberDto m : list) {
			System.out.println(m.getRating());
		}
	}
}
