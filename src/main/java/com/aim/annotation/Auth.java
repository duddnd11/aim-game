package com.aim.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 사용자 인증 어노테이션
 */
@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE }) // 적용대상
@Retention(RetentionPolicy.RUNTIME) // 정보유지 대상
@Documented
public @interface Auth {
	boolean errorOnInvalidType() default false;
	
	String expression() default "";
}

