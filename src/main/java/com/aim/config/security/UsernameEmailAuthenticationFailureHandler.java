package com.aim.config.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UsernameEmailAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("UsernameEmailAuthenticationFailureHandler");
		
		String loginId = request.getParameter("loginId");
		String email = request.getParameter("email");
		String failureUrl="/member/password?error";
		
		if(exception.getClass() == InternalAuthenticationServiceException.class) {
			if(loginId.isBlank()) {
				failureUrl+="=idBlank";
			}else if(email.isBlank()) {
				failureUrl+="=emailBlank";
			}
		}
		
		if(exception.getClass() == BadCredentialsException.class) {
			failureUrl+="=badCredentials";
		}
		
		setDefaultFailureUrl(failureUrl);
		super.onAuthenticationFailure(request, response, exception);
	}
	
}
