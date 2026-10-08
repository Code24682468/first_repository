package com.galym.company_service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CompanyRequest {

    private Long id;
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "The name must not be more than two hundred fifty-five characters")
    private String name;
    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "The description should not be more than a thousand characters")
    private String description;
    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "The rating cannot be less than 0.0")
    @DecimalMax(value = "5.0", inclusive = true, message = "The rating cannot be more than 5.0")
    @Digits(integer = 1, fraction = 1, message = "The rating must consist of two digits")
    private BigDecimal rating;

    public CompanyRequest() {
    }

    public CompanyRequest(String name, String description, BigDecimal rating) {
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

}
