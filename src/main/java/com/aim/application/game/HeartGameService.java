package com.aim.application.game;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.game.dto.HeartGameDto;
import com.aim.domain.game.entity.Game;
import com.aim.domain.game.entity.HeartGame;
import com.aim.domain.member.entity.Member;
import com.aim.exception.NotAuthException;
import com.aim.infrastructure.game.GameRepository;
import com.aim.infrastructure.game.HeartGameRepository;
import com.aim.infrastructure.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeartGameService {
	private final HeartGameRepository heartGameRepository;
	private final GameRepository gameRepository;
	private final MemberRepository memberRepository;
	
	@Transactional
	public HeartGameDto heartGame(Long gameId, Long memberId) {
		Optional<HeartGame> heartGameOp = heartGameRepository.findByGame_GameIdAndMember_MemberId(gameId, memberId);
		
		HeartGame heartGame;
		if(heartGameOp.isPresent()) {
			heartGame = heartGameOp.get();
			heartGame.changeUse();
		}else {
			Game game = gameRepository.findById(gameId).orElseThrow();
			Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotAuthException("로그인 정보 없음"));
			heartGame = new HeartGame(game,member);
			heartGameRepository.save(heartGame);
		}
		
		return new HeartGameDto(heartGame);
	}
}
