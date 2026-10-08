package com.galym.company_service.service;

import com.galym.company_service.dto.CompanyRequest;
import com.galym.company_service.dto.CompanyResponse;
import com.galym.company_service.dto.CompanySearchFilter;
import com.galym.company_service.dto.ReviewRequest;

import java.util.List;

public interface CompanyService {

    List <CompanyResponse> getAllCompanies (CompanySearchFilter filter);

    CompanyResponse getCompany (Long id);

    CompanyResponse createCompany (CompanyRequest companyRequest);

    CompanyResponse updateCompany (Long id, CompanyRequest companyRequest);

    void deleteCompany (Long id);

    void updateCompanyRating (ReviewRequest reviewRequest);

}
