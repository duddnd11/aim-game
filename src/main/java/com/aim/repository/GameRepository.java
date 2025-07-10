package com.aim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aim.domain.Game;

public interface GameRepository extends JpaRepository<Game,Long>{
	
//	@Query("select g from Game g where g.member=:member and g.useYn <> 'N'")
//	List<Game> findByMember(@Param("member") Member member);
	
	@Query("select g from Game g where g.member.role='ROLE_ADMIN' and g.useYn <> 'N'")
	List<Game> findByAdmin();
	
	Optional<Game> findByGameIdAndMember_MemberId(Long gameId, Long memberId);
	
//	@Query("select g from Game g where g.gameId in :gameIds")
//	List<Game> findByGameIds(@Param("gameIds") List<Long> gameIds);
}
