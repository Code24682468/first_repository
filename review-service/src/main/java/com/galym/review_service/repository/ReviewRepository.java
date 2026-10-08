package com.galym.review_service.repository;

import com.galym.review_service.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository <Review, Long> {

    List <Review> findByCompanyId(Long companyId);

    @Query("""
            SELECT r FROM Review r
            WHERE (:title IS NULL OR r.title = :title)
            AND (:rating IS NULL OR r.rating = :rating)
            AND (:companyId IS NULL OR r.companyId = :companyId)
            """)
    List <Review> getAllReviewsFilter (
            @Param("title") String title,
            @Param("rating") BigDecimal rating,
            @Param("companyId") Long companyId,
            Pageable pageable
    );

}
