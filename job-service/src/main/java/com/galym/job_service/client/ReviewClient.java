package com.galym.job_service.client;

import com.galym.job_service.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "REVIEW-SERVICE", contextId = "reviewClient", url = "${review-service.url}")
public interface ReviewClient {

    @GetMapping("/api/reviews")
    public List <Review> getAllReviews (
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "rating", required = false) BigDecimal rating,
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    );

}
