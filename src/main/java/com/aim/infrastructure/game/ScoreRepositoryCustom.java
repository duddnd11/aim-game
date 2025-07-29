package com.aim.infrastructure.game;

import com.aim.domain.SliceDto;
import com.aim.domain.game.dto.ScoreDto;

public interface ScoreRepositoryCustom {
	SliceDto<ScoreDto> findScoreStat(Long gameId, Long memberId, int offset);
}
