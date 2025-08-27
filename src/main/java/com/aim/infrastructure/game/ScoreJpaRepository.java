package com.aim.infrastructure.game;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aim.application.game.dto.ScoreCountResult;
import com.aim.domain.game.entity.Score;
import com.aim.domain.member.entity.Member;

public interface ScoreJpaRepository extends JpaRepository<Score,Long>{
	@Query("select s from Score s "
			+ "join fetch s.member m "
			+ "left join m.profileImg p "
			+ "where s.game.gameId=:gameId and "
			+ "(p.uploadFileId = ("
			+ "select max(p2.uploadFileId) "
			+ "from UploadFile p2 "
			+ "where p2.member = m "
			+ "and p2.type = 'PROFILE' "
			+ ") or p is null) "
			+ "and s.pvp.pvpId is null order by s.totalScore desc")
	Slice<Score> findByGameId(@Param("gameId") Long gameId, Pageable pageable);
	
	Slice<Score> findByGame_GameIdAndMember_MemberId(Long gameId, Long memberId, Pageable pageable);

	@Query("select new com.aim.application.game.dto.ScoreCountResult("
			+ "count(s),"
			+ "count(case when s.pvp is null then 1 end),"
			+ "count(case when s.pvp is not null then 1 end)) "
			+ "from Score s "
			+ "where s.member=:member")
	ScoreCountResult findByScoreCount(@Param("member") Member member);
}
	