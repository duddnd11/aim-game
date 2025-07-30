package com.aim.domain.game.repository;

import java.util.Optional;

import com.aim.domain.game.entity.HeartGame;

public interface HeartGameRepository {
	HeartGame save(HeartGame heartGame);
	
	Optional<HeartGame> findByGame_GameIdAndMember_MemberId(Long gameId, Long memberId);
}
