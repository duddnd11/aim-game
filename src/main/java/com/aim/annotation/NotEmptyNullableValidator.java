package com.aim.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyNullableValidator implements ConstraintValidator<NotEmptyNullable, Object>{

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return value == null || !((String) value).trim().isEmpty();
	}

}
