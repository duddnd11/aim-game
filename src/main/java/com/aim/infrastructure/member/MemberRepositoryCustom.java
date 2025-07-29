package com.aim.infrastructure.member;

import java.util.List;

import com.aim.domain.member.dto.MemberDto;

public interface MemberRepositoryCustom {
	
	int memberRatingRank(Long memberId);
	
	List<MemberDto> memberRatingRankList(int myRank);
}
