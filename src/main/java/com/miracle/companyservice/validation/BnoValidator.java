package com.miracle.companyservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BnoValidator implements ConstraintValidator<Bno, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return value.matches("\\d{3}[-]\\d{2}[-]\\d{5}");
    }
}
