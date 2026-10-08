package com.galym.company_service.dto;

import java.math.BigDecimal;

public class CompanySearchFilter {

    private String name;
    private BigDecimal rating;
    private Integer pageSize;
    private Integer pageNumber;

    public CompanySearchFilter() {

    }

    public CompanySearchFilter(
            String name,
            BigDecimal rating,
            Integer pageSize,
            Integer pageNumber
    ) {
        this.name = name;
        this.rating = rating;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

}
