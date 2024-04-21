package validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameCountConstraintValidator implements ConstraintValidator<NameCount, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value == null) {
			
			return false;
		}
		
		boolean result = value.length() <= 50;
		
		return result;
	}

}
