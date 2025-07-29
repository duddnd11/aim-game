package com.aim.config.security;

import java.lang.annotation.Annotation;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.aim.annotation.Auth;
import com.aim.domain.member.dto.MemberDto;
import com.aim.domain.member.entity.AuthUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
//@RequiredArgsConstructor
public class CustomAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver{

	private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

	private ExpressionParser parser = new SpelExpressionParser();

	private BeanResolver beanResolver;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return findMethodAnnotation(Auth.class, parameter) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		log.info("resolveArgument");
		Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();
		
		if (authentication == null) {
			log.info("authentication == null");
			mavContainer.addAttribute("member", null);
			return null;
		}
		
		Object principal = authentication.getPrincipal();
		Auth annotation = findMethodAnnotation(Auth.class, parameter);
		String expressionToParse = annotation.expression();
		if (StringUtils.hasLength(expressionToParse)) {
			StandardEvaluationContext context = new StandardEvaluationContext();
			context.setRootObject(principal);
			context.setVariable("this", principal);
			context.setBeanResolver(this.beanResolver);
			Expression expression = this.parser.parseExpression(expressionToParse);
			principal = expression.getValue(context);
		}
		
		if (principal != null && !ClassUtils.isAssignable(parameter.getParameterType(), principal.getClass()) || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
			if (annotation.errorOnInvalidType()) {
				throw new ClassCastException(principal + " is not assignable to " + parameter.getParameterType());
			}
			log.info("authentication == null");
			mavContainer.addAttribute("member", null);
			return null;
		}
		
		AuthUser authUser= (AuthUser) principal;
		MemberDto member = new MemberDto(authUser);
		mavContainer.addAttribute("member", member);
		
		return principal;
	}
	
	public void setBeanResolver(BeanResolver beanResolver) {
		this.beanResolver = beanResolver;
	}
	
	public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}

	private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
		T annotation = parameter.getParameterAnnotation(annotationClass);
		if (annotation != null) {
			return annotation;
		}
		Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
		for (Annotation toSearch : annotationsToSearch) {
			annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
			if (annotation != null) {
				return annotation;
			}
		}
		return null;
	}
}
