package net.elmadigital.tutorsmanager.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TutCodeConstraintValidator.class)
public @interface TutCode {

	public String value() default "TUT";
	public String message() default "should start with TUT";
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
}
