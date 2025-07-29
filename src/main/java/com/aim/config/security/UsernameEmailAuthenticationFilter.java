package com.aim.config.security;

import java.io.IOException;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsernameEmailAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "loginId";

	public static final String SPRING_SECURITY_FORM_EMAIL_KEY = "email";

	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/rest/member/password/modify", "GET");

	private String loginIdParameter = SPRING_SECURITY_FORM_USERNAME_KEY;

	private String emailParameter = SPRING_SECURITY_FORM_EMAIL_KEY;

	private boolean getOnly = true;
	
	public UsernameEmailAuthenticationFilter() {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
	}

	public UsernameEmailAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
	}
	
	public UsernameEmailAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
		super.setAuthenticationSuccessHandler(successHandler);
	}
	
	public UsernameEmailAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
		super.setAuthenticationSuccessHandler(successHandler);
		super.setAuthenticationFailureHandler(failureHandler);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		log.info("attemptAuthentication");
		if (this.getOnly && !request.getMethod().equals("GET")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String loginId = obtainLoginId(request);
		loginId = (loginId != null) ? loginId.trim() : "";
		String email = obtainEmail(request);
		email = (email != null) ? email : "";
		UsernameEmailAuthenticationToken authRequest = UsernameEmailAuthenticationToken.unauthenticated(loginId,email);
		log.info("authRequest:"+authRequest);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	@Nullable
	protected String obtainEmail(HttpServletRequest request) {
		return request.getParameter(this.emailParameter);
	}
	
	@Nullable
	protected String obtainLoginId(HttpServletRequest request) {
		return request.getParameter(this.loginIdParameter);
	}

	protected void setDetails(HttpServletRequest request, UsernameEmailAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	public void setUsernameParameter(String loginIdParameter) {
		Assert.hasText(loginIdParameter, "LoginId parameter must not be empty or null");
		this.loginIdParameter = loginIdParameter;
	}

	public void setEmailParameter(String emailParameter) {
		Assert.hasText(emailParameter, "Email parameter must not be empty or null");
		this.emailParameter = emailParameter;
	}

	public void setGetOnly(boolean getOnly) {
		this.getOnly = getOnly;
	}

	public final String getLoginIdParameter() {
		return this.loginIdParameter;
	}

	public final String getEmailParameter() {
		return this.emailParameter;
	}
	
}
