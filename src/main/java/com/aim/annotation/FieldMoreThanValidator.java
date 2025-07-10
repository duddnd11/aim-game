package com.aim.annotation;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldMoreThanValidator implements ConstraintValidator<FieldMoreThan, Object> {
	private String firstFieldName;
	private String secondFieldName;
	private String message;
	
	@Override
	public void initialize(FieldMoreThan constraintAnnotation) {
		firstFieldName = constraintAnnotation.field();
	    secondFieldName = constraintAnnotation.compareTo();
	    message = constraintAnnotation.message();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// 첫번째 값이 두번째 값보다 큰지 확인
		log.info("FieldMoreThanValidator");
		Integer firstObj = (Integer)new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
		Integer secondObj = (Integer)new BeanWrapperImpl(value).getPropertyValue(secondFieldName);
	    boolean valid = firstObj == null && secondObj == null || firstObj != null && firstObj.compareTo(secondObj) >= 0;
	    
	    if (!valid) {
	    	// 기본 메시지 비활성화
	    	context.disableDefaultConstraintViolation();

	    	// 커스템 메시지 설정
	    	context.buildConstraintViolationWithTemplate(message)
	    		.addPropertyNode(firstFieldName)
	    		.addConstraintViolation();
	    }
	    return valid;
	}

}
