package com.galym.company_service.service;

import com.galym.company_service.clients.ReviewClient;
import com.galym.company_service.dto.CompanyRequest;
import com.galym.company_service.dto.CompanyResponse;
import com.galym.company_service.dto.ReviewRequest;
import com.galym.company_service.dto.CompanySearchFilter;
import com.galym.company_service.mapper.CompanyMapper;
import com.galym.company_service.model.Company;
import com.galym.company_service.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final ReviewClient reviewClient;

    @Autowired
    public CompanyServiceImpl (CompanyRepository companyRepository, CompanyMapper companyMapper, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.reviewClient = reviewClient;
    }

    @Override
    public List <CompanyResponse> getAllCompanies (CompanySearchFilter filter) {
        log.info("Called getAllCompanies");
        var pageSize = filter.getPageSize() != null
                ? filter.getPageSize() : 10;
        var pageNumber = filter.getPageNumber() != null
                ? filter.getPageNumber() : 0;
        var pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);
        List <Company> companyList = companyRepository.getAllCompaniesFilter(
                filter.getName(),
                filter.getRating(),
                pageable
        );
        return companyList.stream()
                .map(companyMapper::toResponse)
                .toList();
    }

    @Override
    public CompanyResponse getCompany (Long id) {
        log.info("Called getCompany");
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found company by id: " + id));
        return companyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse createCompany (CompanyRequest companyRequest) {
        log.info("Called createCompany");
        if(companyRequest.getId() != null) {
            throw new IllegalArgumentException("Company id must be null");
        }
        Company company = companyMapper.toCompany(companyRequest);
        Company companySaved = companyRepository.save(company);
        return companyMapper.toResponse(companySaved);
    }

    @Override
    public CompanyResponse updateCompany (Long id, CompanyRequest companyRequest) {
        log.info("Called updateCompany");
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found company by id: " + id));
        if(companyRequest.getId() != null) {
            throw new IllegalArgumentException("You cannot change the company id");
        }
        company.setName(companyRequest.getName());
        company.setDescription(companyRequest.getDescription());
        company.setRating(companyRequest.getRating());
        Company companySaved = companyRepository.save(company);
        return companyMapper.toResponse(companySaved);
    }

    @Override
    public void deleteCompany (Long id) {
        log.info("Called deleteCompany");
        if(! companyRepository.existsById(id)) {
            throw new EntityNotFoundException("Not found company by id: " + id);
        }
        companyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateCompanyRating (ReviewRequest reviewRequest) {
        log.info("Called updateCompanyRating");
        Company company = companyRepository.findById(reviewRequest.getCompanyId())
                .orElse(null);
        if(company == null) {
            log.warn("Not found Company by id: " + reviewRequest.getCompanyId());
            return;
        }
        BigDecimal averageRating = reviewClient.getAverageRating(reviewRequest.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }

}
