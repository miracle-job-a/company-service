package com.miracle.companyservice.validation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BnoValidator.class)
public @interface Bno {
    String message() default "사업자번호 형식이 일치하지 않습니다.";
}
