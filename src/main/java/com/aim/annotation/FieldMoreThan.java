package com.aim.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD, PARAMETER, ANNOTATION_TYPE, TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMoreThanValidator.class)
@Documented
public @interface FieldMoreThan {
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
	String message() default "";
	
	String field();
	
	String compareTo();
	
	@Target({TYPE, ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
		FieldMoreThan[] value();
    }
}
