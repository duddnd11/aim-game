package com.aim.application;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aim.application.member.service.CertificationService;
import com.aim.domain.member.repository.MemberRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {

	private final MemberRepository memberRepository;
	private final JavaMailSender mailSender;
	private final CertificationService certificationService;
	
	@Transactional
	public void certifyMailSend(String email) {
		memberRepository.findByEmail(email).orElseThrow();
		
		String randomNum = certificationService.makeRandomNumber(email);

		// 메일 보내기
		MimeMessage message = mailSender.createMimeMessage();
		try {
			message.addRecipients(RecipientType.TO, email);
			message.setSubject("인증번호");
			
			String msgStr = "인증번호는 " + randomNum +" 입니다.";
			
			message.setText(msgStr,"utf-8");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		mailSender.send(message);
	}
	
}
