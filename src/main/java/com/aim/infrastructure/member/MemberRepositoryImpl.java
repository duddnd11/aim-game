package com.aim.infrastructure.member;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.aim.domain.QMember;
import com.aim.domain.member.dto.MemberDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{
	private final JPAQueryFactory queryFactory;
	private final JdbcTemplate jdbcTemplate;
	private static QMember qMember = QMember.member;
	
	@Override
	public int memberRatingRank(Long memberId) {
		String sql = "SELECT ranking FROM "
				+ "(SELECT member_id, rating, RANK() OVER(ORDER BY rating DESC) as ranking FROM member) as rankingData"
				+ " WHERE member_id=?";
		int ranking =  jdbcTemplate.queryForObject(sql, Integer.class,memberId);
		
		return ranking;
	}	
	
	@Override
	public List<MemberDto> memberRatingRankList(int page) {
		int pageSize = 10;
		
		List<MemberDto> memberRatingRank = queryFactory
						.select(Projections.constructor(MemberDto.class, qMember))
						.from(qMember)
						.orderBy(qMember.rating.desc())
						.offset((page-1) * pageSize)
						.limit(pageSize)
						.fetch();
		
		return memberRatingRank;
	}

}
