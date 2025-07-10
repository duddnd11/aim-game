package com.aim.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD, PARAMETER, ANNOTATION_TYPE, TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyNullableValidator.class)
@Documented
public @interface NotEmptyNullable {
	String message() default ""; // 검증 실패시 기본 메시지
	
	Class<?>[] groups() default { }; // 검증 그룹 지정
	
	Class<? extends Payload>[] payload() default { }; // 메타데이터 정보
}
