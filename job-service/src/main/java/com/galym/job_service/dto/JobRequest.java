package com.galym.job_service.dto;

import com.galym.job_service.annotation.SalaryRangeValid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@SalaryRangeValid
public class JobRequest {

    private Long id;
    @NotBlank(message = "Required is title")
    @Size(max = 255, message = "The title must not be more than two hundred fifty-five characters")
    private String title;
    @NotBlank(message = "Required is description")
    @Size(max = 1000, message = "The description should not be more than a thousand characters")
    private String description;
    @NotNull(message = "Required is minSalary")
    @DecimalMin(value = "0.0", inclusive = false, message = "The minSalary cannot be less than 1.0")
    private BigDecimal minSalary;
    @NotNull(message = "Required is maxSalary")
    @DecimalMin(value = "0.0", inclusive = false, message = "The maxSalary cannot be less than 1.0")
    private BigDecimal maxSalary;
    @NotBlank(message = "Required is location")
    @Size(max = 255, message = "The location must not be more than two hundred fifty-five characters")
    private String location;
    @NotNull(message = "Required is companyId")
    @Min(value = 1, message = "The companyId cannot be less than 1")
    private Long companyId;

    public JobRequest() {
    }

    public JobRequest(String title, String description, BigDecimal minSalary, BigDecimal maxSalary, String location, Long companyId) {
        this.title = title;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}
