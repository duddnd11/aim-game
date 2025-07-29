package com.aim.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aim.config.security.CustomAuthenticationPrincipalArgumentResolver;
import com.aim.config.security.UsernameEmailAuthenticationFilter;
import com.aim.config.security.UsernameEmailAuthenticationPrincipalArgumentResolver;
import com.aim.config.security.UsernameEmailAuthenticationProvider;
import com.aim.domain.game.dto.PvpMatchingDto;
import com.aim.domain.game.dto.PvpMatchingMemberDto;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UsernameEmailAuthenticationProvider usernameEmailAuthenticationProvider;
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// 커스텀 argument 추가 (인증객체 예외처리)
		resolvers.add(new CustomAuthenticationPrincipalArgumentResolver());
		resolvers.add(new UsernameEmailAuthenticationPrincipalArgumentResolver());
	}
	
	@Bean
    public void configureGlobal() throws Exception {
		// 커스텀 프로바이더 등록하기전에 기존 프로바이더 설정 (설정 안하면 기존 프로바이더가 날라감)
		authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
		// 커스텀 프로바이더 추가
		authenticationManagerBuilder.authenticationProvider(usernameEmailAuthenticationProvider);
    }
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return daoAuthenticationProvider;
	}
	
//	@Bean
	public UsernameEmailAuthenticationFilter usernameEmailAuthenticationFilter() throws Exception {
		UsernameEmailAuthenticationFilter filter = new UsernameEmailAuthenticationFilter();
//        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/member/password?error"));
        return filter;
    }
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
		//컨버터 추가
        registry.addConverter(new BoardTypeConverter());
    }
	
	@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
	
	@Bean
    public ConcurrentHashMap<Long, PvpMatchingDto> pvpGames() {
        return new ConcurrentHashMap<Long, PvpMatchingDto>();
    }
	
	@Bean
	public ConcurrentHashMap<Long, ScheduledFuture<?>> futureMap() {
		return new ConcurrentHashMap<Long, ScheduledFuture<?>>();
	}
	
	@Bean
	public Map<Long,ConcurrentLinkedQueue<PvpMatchingMemberDto>> pvpQueMap(){
		return new HashMap<Long,ConcurrentLinkedQueue<PvpMatchingMemberDto>>();
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload_files/**")
                .addResourceLocations("file:/upload_files/");
    }
}
