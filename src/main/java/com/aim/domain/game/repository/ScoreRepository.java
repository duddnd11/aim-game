package com.aim.domain.game.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.aim.domain.SliceDto;
import com.aim.domain.game.dto.ScoreCountDto;
import com.aim.domain.game.dto.ScoreDto;
import com.aim.domain.game.entity.Score;
import com.aim.domain.member.entity.Member;

public interface ScoreRepository {
	Score save(Score score);
	
	Optional<Score> findById(Long scoreId);
	
	Slice<Score> findByGameId(Long gameId, Pageable pageable);
	
	Slice<Score> findByGame_GameIdAndMember_MemberId(Long gameId, Long memberId, Pageable pageable);
	
	ScoreCountDto findByScoreCount(Member member);
	
	SliceDto<ScoreDto> findScoreStat(Long gameId, Long memberId, int offset);
}
