package com.aim;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.EmailService;

@SpringBootTest
@Transactional
public class EmailTest {
	
	@Autowired
	EmailService emailService;
	
//	@Test
	void 이메일전송() {
		emailService.certifyMailSend("ljk8003@naver.com");
	}
}
