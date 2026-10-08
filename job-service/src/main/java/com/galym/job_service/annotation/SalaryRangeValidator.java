package com.galym.job_service.annotation;

import com.galym.job_service.dto.JobRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class SalaryRangeValidator implements ConstraintValidator <SalaryRangeValid, JobRequest> {

    @Override
    public boolean isValid (JobRequest request, ConstraintValidatorContext context) {
        if(request == null) {
            return true;
        }
        BigDecimal min = request.getMinSalary();
        BigDecimal max = request.getMaxSalary();
        if(min == null || max == null) {
            return true;
        }
        return min.compareTo(max) <= 0;
    }

}


