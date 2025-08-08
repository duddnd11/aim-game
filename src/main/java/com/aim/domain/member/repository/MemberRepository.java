package com.aim.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aim.application.member.dto.MemberResult;
import com.aim.domain.member.entity.Member;

public interface MemberRepository {
	Member save(Member member);
	
	List<Member> findAll();
	
	Optional<Member> findById(Long memberId);
	
	Optional<Member> findByLoginId(String loginId);
	
	Optional<Member> findByEmail(String email);
	
	Optional<Member> findByLoginIdAndEmail(String logindId,String email);
	
	Page<Member> pvpRank(Pageable pageable);
	
	int memberRatingRank(Long memberId);
	
	List<MemberResult> memberRatingRankList(int myRank);
}
