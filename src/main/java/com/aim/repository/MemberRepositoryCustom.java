package com.aim.repository;

import java.util.List;

import com.aim.dto.MemberDto;

public interface MemberRepositoryCustom {
	
	int memberRatingRank(Long memberId);
	
	List<MemberDto> memberRatingRankList(int myRank);
}
