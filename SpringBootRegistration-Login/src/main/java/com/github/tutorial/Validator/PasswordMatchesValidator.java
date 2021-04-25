package com.github.tutorial.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.tutorial.DTO.UserDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		UserDTO user=(UserDTO) value;
		return user.getPassword().equals(user.getMatchesPassword());
	}

}
