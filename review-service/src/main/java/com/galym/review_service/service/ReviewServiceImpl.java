package com.galym.review_service.service;

import com.galym.review_service.client.CompanyClient;
import com.galym.review_service.dto.ReviewRequest;
import com.galym.review_service.dto.ReviewResponse;
import com.galym.review_service.dto.ReviewSearchFilter;
import com.galym.review_service.dto.ReviewWithCompanyDTO;
import com.galym.review_service.exception.LimitRequestException;
import com.galym.review_service.exception.ServiceUnavailableException;
import com.galym.review_service.external.Company;
import com.galym.review_service.model.Review;
import com.galym.review_service.repository.ReviewRepository;
import com.galym.review_service.mapper.ReviewMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    private final CompanyClient companyClient;

    private final Logger log = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    public ReviewServiceImpl (
            ReviewRepository reviewRepository,
            ReviewMapper reviewMapper,
            CompanyClient companyClient
    ) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.companyClient = companyClient;
    }

    @Override
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker")
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List <ReviewWithCompanyDTO> getAllReviews (ReviewSearchFilter filter) {
        log.info("Called getAllReviews");
        Integer pageSize = filter.getPageSize() != null
                ? filter.getPageSize() : 10;
        Integer pageNumber = filter.getPageNumber() != null
                ? filter.getPageNumber() : 0;
        var pageable = Pageable.ofSize(pageSize)
                .withPage(pageNumber);
        List <Review> reviewList = reviewRepository.getAllReviewsFilter(
                filter.getTitle(),
                filter.getRating(),
                filter.getCompanyId(),
                pageable
        );
        List <ReviewWithCompanyDTO> reviewWithCompanyDTOS = new ArrayList <> ();
        for(Review review : reviewList) {
            Company company = companyClient.getCompany(review.getCompanyId());
            ReviewWithCompanyDTO dto = reviewMapper.toReviewWithCompanyDTO(review, company);
            reviewWithCompanyDTOS.add(dto);
        }
        return reviewWithCompanyDTOS;
    }

    @Override
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker")
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public ReviewWithCompanyDTO getReview (Long id) {
        log.info("Called getReview");
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Review by id: " + id));
        Company company = companyClient.getCompany(review.getCompanyId());
        return reviewMapper.toReviewWithCompanyDTO(review, company);
    }

    @Override
    public ReviewResponse createReview (ReviewRequest reviewRequest) {
        log.info("Called createReview");
        if(reviewRequest.getId() != null) {
            throw new IllegalArgumentException("Review id must be null");
        }
        Review review = reviewMapper.toReview(reviewRequest);
        Review reviewSaved = reviewRepository.save(review);
        return reviewMapper.toResponse(reviewSaved);
    }

    @Override
    public ReviewResponse updateReview (Long id, ReviewRequest reviewRequest) {
        log.info("Called updateReview");
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Review by id: " + id));
        if(reviewRequest.getId() != null) {
            throw new IllegalArgumentException("You cannot change the review id");
        }
        review.setTitle(reviewRequest.getTitle());
        review.setDescription(reviewRequest.getDescription());
        review.setRating(reviewRequest.getRating());
        review.setCompanyId(reviewRequest.getCompanyId());
        Review reviewSaved = reviewRepository.save(review);
        return reviewMapper.toResponse(reviewSaved);
    }

    @Override
    public void deleteReview (Long id) {
        log.info("Called deleteReview");
        if(! reviewRepository.existsById(id)) {
            throw new EntityNotFoundException ("Not found Review by id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Override
    public BigDecimal getAverageRating (Long companyId) {
        log.info("Called getAverageRating");
        List <Review> reviewList = reviewRepository.findByCompanyId(companyId);
        if(reviewList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = reviewList.stream()
                .map((Review review) -> {return review.getRating();})
                .reduce(BigDecimal.ZERO,
                        (BigDecimal a, BigDecimal b) -> {
                    return a.add(b);
                });
        return sum.divide(
                BigDecimal.valueOf(reviewList.size()),
                1,
                RoundingMode.HALF_UP);
    }

    public List <ReviewWithCompanyDTO> companyBreakerFallback (Exception e) {
        throw new ServiceUnavailableException("The service is temporarily not available, please try again later");
    }

    public ReviewWithCompanyDTO companyBreakerFallback (Long id, Exception e) {
        throw new ServiceUnavailableException("The service is temporarily not available, please try again later");
    }

    public List <ReviewWithCompanyDTO> companyBreakerFallback (RequestNotPermitted e) {
        throw new LimitRequestException("Too many requests. Try again later.");
    }

    public ReviewWithCompanyDTO companyBreakerFallback (Long id, RequestNotPermitted e) {
        throw new LimitRequestException("Too many requests. Try again later.");
    }

}


