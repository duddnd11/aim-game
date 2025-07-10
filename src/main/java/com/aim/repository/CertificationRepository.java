package com.aim.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aim.domain.Certification;

public interface CertificationRepository extends JpaRepository<Certification,Long>{
	
	@Query("select c from Certification c where email=:email and certificationNumber=:certificationNumber and status='N'")
	Optional<Certification> certifyCheck(@Param("email") String email, @Param("certificationNumber") String certificationNumber);
}
