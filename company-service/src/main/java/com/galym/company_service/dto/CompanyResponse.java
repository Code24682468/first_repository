package com.galym.company_service.dto;

import java.math.BigDecimal;

public class CompanyResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal rating;

    public CompanyResponse() {
    }

    public CompanyResponse(Long id, String name, String description, BigDecimal rating) {
        this.id = id;
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
