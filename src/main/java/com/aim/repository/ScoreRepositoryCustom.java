package com.aim.repository;

import com.aim.dto.ScoreDto;
import com.aim.dto.SliceDto;

public interface ScoreRepositoryCustom {
	SliceDto<ScoreDto> findScoreStat(Long gameId, Long memberId, int offset);
}
