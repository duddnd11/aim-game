package com.aim.infrastructure.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.aim.application.member.dto.MemberResult;
import com.aim.domain.member.entity.Member;
import com.aim.domain.member.entity.QMember;
import com.aim.domain.member.repository.MemberRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{
	private final MemberJpaRepository memberJpaRepository;
	private final JPAQueryFactory queryFactory;
	private final JdbcTemplate jdbcTemplate;
	private static QMember qMember = QMember.member;
	
	@Override
	public Member save(Member member) {
		return memberJpaRepository.save(member);
	}

	@Override
	public List<Member> findAll() {
		return memberJpaRepository.findAll();
	}

	@Override
	public Optional<Member> findById(Long memberId) {
		return memberJpaRepository.findById(memberId);
	}

	@Override
	public Optional<Member> findByLoginId(String loginId) {
		return memberJpaRepository.findByLoginId(loginId);
	}

	@Override
	public Optional<Member> findByEmail(String email) {
		return memberJpaRepository.findByEmail(email);
	}

	@Override
	public Optional<Member> findByLoginIdAndEmail(String logindId, String email) {
		return memberJpaRepository.findByLoginIdAndEmail(logindId, email);
	}

	@Override
	public Page<Member> pvpRank(Pageable pageable) {
		return memberJpaRepository.pvpRank(pageable);
	}
	
	@Override
	public int memberRatingRank(Long memberId) {
		String sql = "SELECT ranking FROM "
				+ "(SELECT member_id, rating, RANK() OVER(ORDER BY rating DESC) as ranking FROM member) as rankingData"
				+ " WHERE member_id=?";
		int ranking =  jdbcTemplate.queryForObject(sql, Integer.class,memberId);
		
		return ranking;
	}	
	
	@Override
	public List<MemberResult> memberRatingRankList(int page) {
		int pageSize = 10;
		
		List<MemberResult> memberRatingRank = queryFactory
						.select(Projections.constructor(MemberResult.class, qMember))
						.from(qMember)
						.orderBy(qMember.rating.desc())
						.offset((page-1) * pageSize)
						.limit(pageSize)
						.fetch();
		
		return memberRatingRank;
	}
}
