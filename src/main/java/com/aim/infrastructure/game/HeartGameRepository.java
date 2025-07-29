package com.aim.infrastructure.game;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aim.domain.game.entity.HeartGame;

public interface HeartGameRepository extends JpaRepository<HeartGame,Long>{
	Optional<HeartGame> findByGame_GameIdAndMember_MemberId(Long gameId, Long memberId);
}
