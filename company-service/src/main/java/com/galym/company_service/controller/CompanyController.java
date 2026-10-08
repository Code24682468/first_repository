package com.galym.company_service.controller;

import com.galym.company_service.dto.CompanyRequest;
import com.galym.company_service.dto.CompanyResponse;
import com.galym.company_service.dto.CompanySearchFilter;
import com.galym.company_service.service.CompanyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {

    private final CompanyService companyService;
    private final Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    public CompanyController (CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity <List<CompanyResponse>> getAllCompanies (
            @RequestParam(name = "name", required = false) String name,
            @RequestParam (name = "rating", required = false) BigDecimal rating,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
            ) {
        log.info("Called getAllCompanies");
        var filter = new CompanySearchFilter(
                name,
                rating,
                pageSize,
                pageNumber
        );
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompanies(filter));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity <CompanyResponse> getCompany (@PathVariable Long id) {
        log.info("Called getCompany");
            return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompany(id));
    }

    @PostMapping("/companies")
    public ResponseEntity <CompanyResponse> createCompany (@RequestBody @Valid CompanyRequest companyRequest) {
        log.info("Called createCompany");
        return new ResponseEntity <> (companyService.createCompany(companyRequest), HttpStatus.CREATED);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity <CompanyResponse> updateCompany (@PathVariable Long id, @RequestBody @Valid CompanyRequest companyRequest) {
        log.info("Called updateCompany");
        return new ResponseEntity <> (companyService.updateCompany(id, companyRequest), HttpStatus.OK);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity <String> deleteCompany (@PathVariable Long id) {
        log.info("Called deleteCompany");
        companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted company with id = " + id);
    }

}
