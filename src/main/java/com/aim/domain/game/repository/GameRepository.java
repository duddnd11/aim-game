package com.aim.domain.game.repository;

import java.util.List;
import java.util.Optional;

import com.aim.application.game.dto.GameResult;
import com.aim.domain.game.entity.Game;

public interface GameRepository {
	Game save(Game game);
	
	List<Game> findAll();
	
	Optional<Game> findById(Long gameId);
	
	List<Game> findByAdmin();
	
	Optional<Game> findByGameIdAndMember_MemberId(Long gameId, Long memberId);
	
	List<GameResult> findMainGame(Long memberId);
	
	List<GameResult> findByHeartGame(Long memberId);
	
	List<GameResult> findByMember(Long memberId);
}
