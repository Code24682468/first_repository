package com.galym.job_service.dto;

import java.math.BigDecimal;

public class JobSearchFilter {

    private String title;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String location;
    private Long companyId;
    private Integer pageSize;
    private Integer pageNumber;

    public JobSearchFilter() {
    }

    public JobSearchFilter(
            String title,
            BigDecimal minSalary,
            BigDecimal maxSalary,
            String location,
            Long companyId,
            Integer pageSize,
            Integer pageNumber
    ) {
        this.title = title;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
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

    public Integer getPageSize () {
        return pageSize;
    }

    public void setPageSize (Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber () {
        return pageNumber;
    }

    public void setPageNumber (Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

}
