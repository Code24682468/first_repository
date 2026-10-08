package com.galym.review_service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ReviewRequest {

    private Long id;
    @NotBlank(message = "Required is title")
    @Size(max = 255, message = "The title must not be more than two hundred fifty-five characters")
    private String title;
    @NotBlank(message = "Required is description")
    @Size(max = 1000, message = "The description should not be more than a thousand characters")
    private String description;
    @NotNull(message = "Required is rating")
    @DecimalMin(value = "0.0", inclusive = true, message = "The rating cannot be less than 0.0")
    @DecimalMax(value = "5.0", inclusive = true, message = "The rating cannot be more than 5.0")
    @Digits(integer = 1, fraction = 1, message = "The rating must consist of two digits")
    private BigDecimal rating;
    @NotNull(message = "Required is companyId")
    @Min(value = 1, message = "The companyId cannot be less than 1")
    private Long companyId;

    public ReviewRequest() {
    }

    public ReviewRequest(String title, String description, BigDecimal rating, Long companyId) {
        this.title = title;
        this.description = description;
        this.rating = rating;
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

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}
