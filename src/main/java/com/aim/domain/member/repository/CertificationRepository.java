package com.aim.domain.member.repository;

import java.util.Optional;

import com.aim.domain.member.entity.Certification;

public interface CertificationRepository {
	Certification save(Certification certification);
	
	Optional<Certification> certifyCheck(String email, String certificationNumber);
}
