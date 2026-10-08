package com.galym.review_service.mapper;

import com.galym.review_service.dto.ReviewRequest;
import com.galym.review_service.dto.ReviewResponse;
import com.galym.review_service.dto.ReviewWithCompanyDTO;
import com.galym.review_service.external.Company;
import com.galym.review_service.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponse toResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setId(review.getId());
        reviewResponse.setTitle(review.getTitle());
        reviewResponse.setDescription(review.getDescription());
        reviewResponse.setRating(review.getRating());
        reviewResponse.setCompanyId(review.getCompanyId());
        return reviewResponse;
    }

    public Review toReview (ReviewRequest reviewRequest) {
        Review review = new Review ();
        review.setTitle(reviewRequest.getTitle());
        review.setDescription(reviewRequest.getDescription());
        review.setRating(reviewRequest.getRating());
        review.setCompanyId(reviewRequest.getCompanyId());
        return review;
    }

    public ReviewWithCompanyDTO toReviewWithCompanyDTO (Review review, Company company) {

        ReviewWithCompanyDTO reviewWithCompanyDTO = new ReviewWithCompanyDTO ();
        reviewWithCompanyDTO.setId(review.getId());
        reviewWithCompanyDTO.setTitle(review.getTitle());
        reviewWithCompanyDTO.setDescription(review.getDescription());
        reviewWithCompanyDTO.setRating(review.getRating());
        reviewWithCompanyDTO.setCompany(company);

        return reviewWithCompanyDTO;

    }

}
