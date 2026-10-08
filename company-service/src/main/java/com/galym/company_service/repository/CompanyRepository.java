package com.galym.company_service.repository;

import com.galym.company_service.model.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Long> {

    @Query("""
            SELECT c FROM Company c
            WHERE (:name IS NULL OR c.name = :name) 
            AND (:rating IS NULL OR c.rating = :rating)
            """)
    List <Company> getAllCompaniesFilter (
            @Param("name") String name,
            @Param("rating")BigDecimal rating,
            Pageable pageable
            );

}
