package com.aim;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.EmailService;
import com.aim.application.member.MemberService;

@SpringBootTest
@Rollback
@Transactional(readOnly = true)
public class CertificationTest {
	
	@Autowired EmailService emailService;
	@Autowired MemberService memberService;

//	@Test
	void 인증번호() {
//		emailService.certifyMailSend("test@test.com");
		memberService.findByLoginIdAndEmail("test2","test@test.com");
	}
}
