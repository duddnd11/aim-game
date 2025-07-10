package com.aim.annotation;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DisabledFieldValidator implements ConstraintValidator<DisabledField, Object> {
	private String[] fieldNames;
	
	@Override
	public void initialize(DisabledField constraintAnnotation) {
		fieldNames = constraintAnnotation.fieldNames();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		boolean valid = true;
		BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
		for (String fieldName : fieldNames) {
	        boolean fieldDisabled = (Boolean) beanWrapper.getPropertyValue(fieldName);
	
	        if (fieldDisabled) {
	            String correspondingFieldName = fieldName.replace("Disabled", "");
	            Object fieldValue = beanWrapper.getPropertyValue(correspondingFieldName);
	            //System.out.println(correspondingFieldName+":"+fieldValue);
		        if (fieldValue == null || fieldValue.toString().isEmpty()) {
		            context.disableDefaultConstraintViolation();
		            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
		                   .addPropertyNode(correspondingFieldName)
		                   .addConstraintViolation();
		            
		            valid = true;
		        }
	        }
		}
	
		return valid;
	}

}
