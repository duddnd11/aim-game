package com.aim.infrastructure.member;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aim.domain.member.entity.Certification;
import com.aim.domain.member.repository.CertificationRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CertificationRepositoryImpl implements CertificationRepository{
	private final CertificationJpaRepository certificationJpaRepository;
	
	@Override
	public Certification save(Certification certification) {
		return certificationJpaRepository.save(certification);
	}
	
	@Override
	public Optional<Certification> certifyCheck(String email, String certificationNumber) {
		return certificationJpaRepository.certifyCheck(email, certificationNumber);
	}
}
