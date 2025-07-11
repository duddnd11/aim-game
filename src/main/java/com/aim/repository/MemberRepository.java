package com.aim.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aim.domain.Member;

public interface MemberRepository extends JpaRepository<Member,Long>{
	Optional<Member> findByLoginId(String loginId);
	
	Optional<Member> findByEmail(String email);
	
	Optional<Member> findByLoginIdAndEmail(String logindId,String email);
	
	@Query("select m from Member m order by m.rating desc")
	Page<Member> pvpRank(Pageable pageable);
}
