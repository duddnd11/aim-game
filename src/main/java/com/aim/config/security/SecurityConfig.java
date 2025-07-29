package com.aim.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UsernameEmailAuthenticationSuccessHandler usernameEmailAuthenticationSuccessHandler;
	private final UsernameEmailAuthenticationFailureHandler usernameEmailAuthenticationFailureHandler;
	private final UsernamePasswordAuthenticationFailureHandler usernamePasswordAuthenticationFailureHandler;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrfConfig) ->
	        		csrfConfig.disable()
	        )
			.authorizeHttpRequests((authorizeHttpRequests)->
									authorizeHttpRequests
									.requestMatchers("/rest/certify").hasRole("PW_REISSUE")
									.requestMatchers("/board/write/NOTICE").hasRole("ADMIN")
									.requestMatchers("/game","/game/modify/**","/game/member","/game/heart",
											"/member/mypage","member/modify","/board","/board/write/FREE","/board/modify/**").hasAnyRole("USER","ADMIN")
									.requestMatchers("**").permitAll())
			.exceptionHandling(authenticationManager -> authenticationManager
														.accessDeniedPage("/")
														)
			.formLogin((formLogin) -> 
							formLogin.loginPage("/member/login")
							.usernameParameter("loginId")
							.passwordParameter("password")
							.loginProcessingUrl("/member/loginProc")
							.defaultSuccessUrl("/")
							.failureHandler(usernamePasswordAuthenticationFailureHandler)
							)
			.logout((logout)-> 
						logout.deleteCookies("JSESSIONID","remember-me")
						.invalidateHttpSession(true)
						.logoutUrl("/member/logout")
						.logoutSuccessUrl("/")
						)
			.sessionManagement((sessionManagement) -> 
							sessionManagement.maximumSessions(-1).sessionRegistry(sessionRegistry())
					)
//			.addFilterAfter(
//					new UsernameEmailAuthenticationFilter(authenticationManagerBuilder.getObject(),usernameEmailAuthenticationSuccessHandler
//							, usernameEmailAuthenticationFailureHandler)
//					, BasicAuthenticationFilter.class)
			;
		
		return http.build();
	}
	
	
}
	
