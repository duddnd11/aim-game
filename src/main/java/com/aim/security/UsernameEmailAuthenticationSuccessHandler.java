package com.aim.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
@Slf4j
@Component
public class UsernameEmailAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
		log.info("UsernameEmailOnAuthenticationSuccess");
		SecurityContext context = securityContextHolderStrategy.createEmptyContext();
		context.setAuthentication(authentication);
		securityContextHolderStrategy.setContext(context);
		securityContextRepository.saveContext(context, request, response);
		response.sendRedirect("/member/password/modify");
    }
}
*/
@Slf4j
@Component
public class UsernameEmailAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("UsernameEmailOnAuthenticationSuccess");
//		SecurityContext context = securityContextHolderStrategy.createEmptyContext();
//		context.setAuthentication(authentication);
//		securityContextHolderStrategy.setContext(context);
//		securityContextRepository.saveContext(context, request, response);
		
	}
}
