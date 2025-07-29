package com.aim.config.security;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.aim.application.member.MemberService;
import com.aim.domain.member.entity.AuthUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class UsernameEmailAuthenticationProvider implements AuthenticationProvider {
	
	private final MemberService memberService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("UsernameEmailAuthenticationProvider");
		String loginId = authentication.getName();
		String email = authentication.getCredentials().toString();
		
		if(loginId.isBlank()|| email.isBlank()) {
			throw new InternalAuthenticationServiceException("AuthException");
		}
		
		log.info("authenticate("+loginId+","+email+")");
		AuthUser authUser = (AuthUser) memberService.loadUserByUsername(loginId);
		
		if(authUser.getUsername().equals(loginId) && authUser.getEmail().equals(email)) {
			long now = (new Date()).getTime();
			Date tokenExpiresIn = new Date(now + 180000);
//			Date tokenExpiresIn = new Date(now + 1000); // 테스트용 1초
			log.info("tokenExpiresIn:"+tokenExpiresIn);
			
			ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
			auth.add(new SimpleGrantedAuthority("ROLE_PW_REISSUE"));
			
			UsernameEmailAuthenticationToken resultToken = new UsernameEmailAuthenticationToken(authUser, authUser.getEmail(), auth, tokenExpiresIn);
			
			return resultToken;
		}
		
		throw new BadCredentialsException("BAD Credentials");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// 해당 토큰에서만 provider 사용. (UsernameEmailAuthenticationToken)
		return UsernameEmailAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
