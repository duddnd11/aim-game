package com.aim.infrastructure.game;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aim.domain.game.entity.HeartGame;
import com.aim.domain.game.repository.HeartGameRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HeartGameRepositoryImpl implements HeartGameRepository{

	private final HeartGameJpaRepository heartGameJpaRepository;
	
	@Override
	public HeartGame save(HeartGame heartGame) {
		return heartGameJpaRepository.save(heartGame);
	}
	
	@Override
	public Optional<HeartGame> findByGame_GameIdAndMember_MemberId(Long gameId, Long memberId) {
		return heartGameJpaRepository.findByGame_GameIdAndMember_MemberId(gameId, memberId);
	}

}
