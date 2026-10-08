package com.galym.review_service.dto;

import com.galym.review_service.external.Company;

import java.math.BigDecimal;

public class ReviewWithCompanyDTO {

    private Long id;
    private String title;
    private String description;
    private BigDecimal rating;
    private Company company;

    public ReviewWithCompanyDTO() {
    }

    public ReviewWithCompanyDTO(Long id, String title, String description, BigDecimal rating, Company company) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
