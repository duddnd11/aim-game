package com.aim.application.member;

import java.util.Random;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.domain.YnType;
import com.aim.domain.member.entity.Certification;
import com.aim.infrastructure.member.CertificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CertificationService {
	
	private final CertificationRepository certificationRepository;
	private final MessageSource ms;

	/**
	 * 랜덤 인증번호 생성 (6자리 난수)
	 */
	@Transactional
	public String makeRandomNumber(String email) {
		Random random = new Random();
		int randomNum = random.nextInt(1000000);
        
        // 생성된 숫자를 6자리로 맞추기 위해 문자열로 변환하고 앞에 0을 추가
        String formattedRandomNumber = String.format("%06d", randomNum);
		
		Certification certification = new Certification(email, formattedRandomNumber);
		
		certificationRepository.save(certification);
		
		return formattedRandomNumber;
	}
	
	/**
	 * 인증번호 확인 -> 인증상태 변경
	 * @param email
	 * @param certificationNumber
	 * @return
	 */
	@Transactional
	public YnType certifyCheck(String email, String certificationNumber) {
		Certification certify = certificationRepository.certifyCheck(email, certificationNumber)
				.orElseThrow(() -> new IllegalArgumentException(ms.getMessage("member.cerfify.check", null, null)));
		
		certify.changeStatusY();
		return certify.getStatus();
	}
}
