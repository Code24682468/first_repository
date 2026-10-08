package com.galym.company_service.mapper;

import com.galym.company_service.dto.CompanyRequest;
import com.galym.company_service.dto.CompanyResponse;
import com.galym.company_service.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyResponse toResponse (Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setDescription(company.getDescription());
        companyResponse.setRating(company.getRating());
        return companyResponse;
    }

    public Company toCompany (CompanyRequest companyRequest) {
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setDescription(companyRequest.getDescription());
        company.setRating(companyRequest.getRating());
        return company;
    }

}
