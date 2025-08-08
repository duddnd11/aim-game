package com.aim.interfaces.member.restcontroller;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aim.annotation.UserEmailAuth;
import com.aim.application.member.service.CertificationService;
import com.aim.config.security.UsernameEmailAuthenticationToken;
import com.aim.domain.YnType;
import com.aim.domain.member.entity.AuthUser;
import com.aim.exception.NotAuthException;
import com.aim.interfaces.member.dto.FindPasswordForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest/certify")
@RequiredArgsConstructor
public class CertificationRestController {
	
	private final CertificationService certificationService;
	private final MessageSource ms;
	private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
	
	@PostMapping("")
	public ResponseEntity<Boolean> certifyCheck(@UserEmailAuth AuthUser user, @Validated FindPasswordForm findPasswordForm, BindingResult bindingResult) throws BindException{
		log.info("findPasswordForm:"+findPasswordForm);
		
		if(user == null) {
			throw new NotAuthException(ms.getMessage("member.certify.expiration", null, null));
		}
		
		// 인증번호 유무
		if(findPasswordForm.getCertificationNumber().isBlank()) {
			String[] codes = {"NotBlank"};
			bindingResult.addError(new FieldError("findPasswordForm", "certificationNumber", null, false, codes, null, ms.getMessage("member.certify-number.notblank", null, null)));
			throw new BindException(bindingResult);
		}
		
		// 기존 토큰에 user 정보와 현재 들어온 값이 다르지 않은지 검증
		if(!(user.getUsername().equals(findPasswordForm.getLoginId())) || !(user.getEmail().equals(findPasswordForm.getEmail()))) {
			throw new NotAuthException(ms.getMessage("member.certify.expiration", null, null));
		}
		
		YnType certifyStatus = certificationService.certifyCheck(user.getEmail(), findPasswordForm.getCertificationNumber());
		
		if(YnType.Y.equals(certifyStatus)) {
			UsernameEmailAuthenticationToken authentication = (UsernameEmailAuthenticationToken) securityContextHolderStrategy.getContext().getAuthentication();
			authentication.changeStatusY();
			return ResponseEntity.ok().body(true);
		}
		
		return ResponseEntity.ok().body(false);
	}
}
