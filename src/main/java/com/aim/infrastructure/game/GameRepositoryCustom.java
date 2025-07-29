package com.aim.infrastructure.game;

import java.util.List;

import com.aim.domain.game.dto.GameDto;

public interface GameRepositoryCustom {
	List<GameDto> findMainGame(Long memberId);
	
	List<GameDto> findByHeartGame(Long memberId);
	
	List<GameDto> findByMember(Long memberId);
}
