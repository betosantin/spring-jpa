package br.com.robertosantin.spring.annotations.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.robertosantin.spring.annotations.NotEmptyList;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {
	
	@Override
	public void initialize(NotEmptyList constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(List value, ConstraintValidatorContext context) {
		return value != null && !value.isEmpty();
	}

}
