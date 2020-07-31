package net.elmadigital.tutorsmanager.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TutCodeConstraintValidator implements ConstraintValidator<TutCode, String> {
	
	private String tutCodePrefix;

	@Override
	public void initialize(TutCode constraintAnnotation) {
		tutCodePrefix = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && value.startsWith(tutCodePrefix);
	}

}
