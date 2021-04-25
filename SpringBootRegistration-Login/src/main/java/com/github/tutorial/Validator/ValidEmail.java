package com.github.tutorial.Validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;

@Email
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE,ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
	String message() default "Invalid Email";
	Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
