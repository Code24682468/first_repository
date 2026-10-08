package com.galym.review_service.controller;

import com.galym.review_service.dto.ReviewRequest;
import com.galym.review_service.messaging.ReviewMessageProducer;
import com.galym.review_service.dto.ReviewResponse;
import com.galym.review_service.dto.ReviewSearchFilter;
import com.galym.review_service.dto.ReviewWithCompanyDTO;
import com.galym.review_service.service.ReviewService;
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
public class ReviewController {

    private final ReviewService reviewService;
    private final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewMessageProducer reviewMessageProducer;

    @Autowired
    public ReviewController (ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping("/reviews")
    public ResponseEntity <List<ReviewWithCompanyDTO>> getAllReviews (
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "rating", required = false) BigDecimal rating,
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        log.info("Called getAllReviews");
        var filter = new ReviewSearchFilter(
                title,
                rating,
                companyId,
                pageSize,
                pageNumber
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(reviewService.getAllReviews(filter));
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity <ReviewWithCompanyDTO> getReview (@PathVariable Long id) {
        log.info("Called getReview");
        var reviewResponse = reviewService.getReview(id);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponse);
    }

    @PostMapping("/reviews")
    public ResponseEntity <ReviewResponse> createReview (@RequestBody @Valid ReviewRequest reviewRequest) {
        log.info("Called createReview");
        var reviewResponse = reviewService.createReview(reviewRequest);
        reviewMessageProducer.sendMessage(reviewRequest);
        return new ResponseEntity(reviewResponse, HttpStatus.CREATED);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity <ReviewResponse> updateReview (@PathVariable Long id, @RequestBody @Valid ReviewRequest reviewRequest) {
        log.info("Called updateReview");
        return new ResponseEntity <> (reviewService.updateReview(id, reviewRequest), HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity <String> deleteReview (@PathVariable Long id) {
        log.info("Called deleteReview");
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted review with id = " + id);
    }

    @GetMapping("/average-rating-review")
    public BigDecimal getAverageRating (@RequestParam Long companyId) {
        BigDecimal averageRating = reviewService.getAverageRating(companyId);
        return averageRating;
    }

}
