package com.aim.security;

import java.io.IOException;
import java.net.URLEncoder;

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
public class UsernamePasswordAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	public UsernamePasswordAuthenticationFailureHandler() {
//		super.setUseForward(true);		
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("customOnAuthenticationFailure");
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String failureUrl="/member/login?error";
		
		if(loginId.isBlank()) {
			failureUrl+="=idBlank";
		}else if(password.isBlank()) {
			failureUrl+="=passwordBlank";
		}else if(exception.getClass() == InternalAuthenticationServiceException.class || exception.getClass() == BadCredentialsException.class) {
			failureUrl+="=bacCredentials";
		}
		
		failureUrl+="&loginId="+URLEncoder.encode(loginId, "UTF-8");
		
		setDefaultFailureUrl(failureUrl);
		super.onAuthenticationFailure(request, response, exception);
	}
}
