package com.aim;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.aim.service.HeartGameService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Rollback
@Transactional
public class HeartGameTest {
	
	@Autowired HeartGameService heartGameService;
	
	@Test
	void 좋아요() {
		heartGameService.heartGame((long)2, (long)3);
	}
}
