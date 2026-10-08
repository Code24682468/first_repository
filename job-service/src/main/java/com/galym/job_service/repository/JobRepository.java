package com.galym.job_service.repository;

import com.galym.job_service.model.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository <Job, Long> {

    @Query("""
            SELECT j FROM Job j
            WHERE (:title IS NULL OR j.title = :title)
            AND (:minSalary IS NULL OR j.minSalary = :minSalary)
            AND (:maxSalary IS NULL OR j.maxSalary = :maxSalary)
            AND (:location IS NULL OR j.location = :location)
            AND (:companyId IS NULL OR j.companyId = :companyId)
            """)
    List <Job> getAllJobsFilter (
            @Param("title") String title,
            @Param("minSalary") BigDecimal minSalary,
            @Param("maxSalary") BigDecimal maxSalary,
            @Param("location") String location,
            @Param("companyId") Long companyId,
            Pageable pageable
            );

}
