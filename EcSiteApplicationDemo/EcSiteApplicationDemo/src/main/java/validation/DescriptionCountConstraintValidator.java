package validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DescriptionCountConstraintValidator implements ConstraintValidator<DescriptionCount, String> {

	
	
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {	
		
		if(value == null) {
			
			return true;
		}
		
		String converted = value.replace("　", " ").trim();
		
		boolean result = converted.length() <= 200;
		
		return result;
	}
}
