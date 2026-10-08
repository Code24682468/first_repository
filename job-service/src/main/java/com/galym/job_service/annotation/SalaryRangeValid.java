package com.galym.job_service.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SalaryRangeValidator.class)
@Documented
public @interface SalaryRangeValid {

    String message() default "minSalary must be less than or equal to maxSalary";

    Class <?> [] groups() default {};

    Class <? extends Payload> [] payload() default {};

}
