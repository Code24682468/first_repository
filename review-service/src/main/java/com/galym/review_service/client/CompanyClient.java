package com.galym.review_service.client;

import com.galym.review_service.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANY-SERVICE", url = "${company-service.url}")
public interface CompanyClient {

    @GetMapping("/api/companies/{id}")
    public Company getCompany (@PathVariable Long id);

}
