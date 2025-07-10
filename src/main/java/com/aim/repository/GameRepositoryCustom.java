package com.aim.repository;

import java.util.List;

import com.aim.dto.GameDto;

public interface GameRepositoryCustom {
	List<GameDto> findMainGame(Long memberId);
	
	List<GameDto> findByHeartGame(Long memberId);
	
	List<GameDto> findByMember(Long memberId);
}
