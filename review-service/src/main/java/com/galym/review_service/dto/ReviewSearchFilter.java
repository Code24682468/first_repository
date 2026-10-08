package com.galym.review_service.dto;

import java.math.BigDecimal;

public class ReviewSearchFilter {

    private String title;
    private BigDecimal rating;
    private Long companyId;
    private Integer pageSize;
    private Integer pageNumber;

    public ReviewSearchFilter() {
    }

    public ReviewSearchFilter(String title, BigDecimal rating, Long companyId, Integer pageSize, Integer pageNumber) {
        this.title = title;
        this.rating = rating;
        this.companyId = companyId;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
