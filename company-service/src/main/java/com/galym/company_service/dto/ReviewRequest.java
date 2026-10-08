package com.galym.company_service.dto;

import java.math.BigDecimal;

public class ReviewRequest {

    private Long id;
    private String title;
    private String description;
    private BigDecimal rating;
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
