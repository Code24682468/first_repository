package com.galym.review_service.service;

import com.galym.review_service.dto.ReviewRequest;
import com.galym.review_service.dto.ReviewResponse;
import com.galym.review_service.dto.ReviewSearchFilter;
import com.galym.review_service.dto.ReviewWithCompanyDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewService {

    List <ReviewWithCompanyDTO> getAllReviews (ReviewSearchFilter filter);

    ReviewWithCompanyDTO getReview (Long id);

    ReviewResponse createReview (ReviewRequest reviewRequest);

    ReviewResponse updateReview (Long id, ReviewRequest reviewRequest);

    void deleteReview (Long id);

    BigDecimal getAverageRating (Long companyId);

}
