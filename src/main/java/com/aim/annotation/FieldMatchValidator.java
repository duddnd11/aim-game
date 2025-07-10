package com.aim.annotation;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
	private String secondFieldName;
	private String message = "";
	  
	@Override
	public void initialize(FieldMatch constraintAnnotation) {
	    firstFieldName = constraintAnnotation.first();
	    secondFieldName = constraintAnnotation.second();
	    message = constraintAnnotation.message();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// 두 필드의 값이 같은지 확인 (비밀번호, 비밀번호 확인)
	    Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
	    Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);
	    boolean valid = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
	    
	    if (!valid) {
	    	// 기본 메시지 비활성화
	    	context.disableDefaultConstraintViolation();

	    	// 커스템 메시지 설정
	    	context.buildConstraintViolationWithTemplate(message)
	    		.addPropertyNode(secondFieldName)
	    		.addConstraintViolation();
	    }
	    return valid;
	}
}
