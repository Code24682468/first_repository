package com.galym.job_service.service;

import com.galym.job_service.client.CompanyClient;
import com.galym.job_service.client.ReviewClient;
import com.galym.job_service.dto.JobRequest;
import com.galym.job_service.dto.JobResponse;
import com.galym.job_service.dto.JobSearchFilter;
import com.galym.job_service.dto.JobWithCompanyAndReviewDTO;
import com.galym.job_service.exception.LimitRequestException;
import com.galym.job_service.exception.ServiceUnavailableException;
import com.galym.job_service.external.Company;
import com.galym.job_service.external.Review;
import com.galym.job_service.model.Job;
import com.galym.job_service.repository.JobRepository;
import com.galym.job_service.mapper.JobMapper;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    private final CompanyClient companyClient;

    private final ReviewClient reviewClient;

    private final Logger log = LoggerFactory.getLogger(JobService.class);

    @Autowired
    public JobServiceImpl (
            JobRepository jobRepository,
            JobMapper jobMapper,
            CompanyClient companyClient,
            ReviewClient reviewClient
    ) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker")
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List <JobWithCompanyAndReviewDTO> getAllJobs (JobSearchFilter filter) {
        log.info("Called getAllJobs");
        Integer pageSize = filter.getPageSize() != null
                ? filter.getPageSize() : 10;
        Integer pageNumber = filter.getPageNumber() != null
                ? filter.getPageNumber() : 0;
        var pageable = Pageable.ofSize(pageSize)
                .withPage(pageNumber);
        List <Job> jobList = jobRepository.getAllJobsFilter(
                filter.getTitle(),
                filter.getMinSalary(),
                filter.getMaxSalary(),
                filter.getLocation(),
                filter.getCompanyId(),
                pageable
        );
        List <JobWithCompanyAndReviewDTO> jobWithCompanyAndReviewDTOS = new ArrayList <> ();
        for(Job job : jobList) {
            Company company = companyClient.getCompany(job.getCompanyId());
            List <Review> reviewList = reviewClient.getAllReviews(
                    null,
                    null,
                    job.getCompanyId(),
                    filter.getPageSize(),
                    filter.getPageNumber()
            );
            JobWithCompanyAndReviewDTO dto = jobMapper.toJobWithCompanyAndReviewDTO(job, company, reviewList);
            jobWithCompanyAndReviewDTOS.add(dto);
        }
        return jobWithCompanyAndReviewDTOS;
    }

    @Override
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker")
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public JobWithCompanyAndReviewDTO getJob (Long id) {
        log.info("Called getJob");
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Job by id: " + id));
        Company company = companyClient.getCompany(job.getCompanyId());
        List <Review> reviewList = reviewClient.getAllReviews(
                null,
                null,
                job.getCompanyId(),
                null,
                null
        );
        return jobMapper.toJobWithCompanyAndReviewDTO(job, company, reviewList);
    }

    @Override
    public JobResponse createJob (JobRequest jobRequest) {
        log.info("Called createJob");
        if(jobRequest.getId() != null) {
            throw new IllegalArgumentException("Job id must be null");
        }
        Job job = jobMapper.toJob(jobRequest);
        Job jobSaved = jobRepository.save(job);
        return jobMapper.toResponse(jobSaved);
    }

    @Override
    public JobResponse updateJob (Long id, JobRequest jobRequest) {
        log.info("Called updateJob");
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Job by id: " + id));
        if(jobRequest.getId() != null) {
            throw new IllegalArgumentException("You cannot change the company id");
        }
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setMinSalary(jobRequest.getMinSalary());
        job.setMaxSalary(jobRequest.getMaxSalary());
        job.setLocation(jobRequest.getLocation());
        job.setCompanyId(jobRequest.getCompanyId());
        var jobSaved = jobRepository.save(job);
        return jobMapper.toResponse(jobSaved);
    }

    @Override
    public void deleteJob (Long id) {
        log.info("Called deleteJob");
        if(! jobRepository.existsById(id)) {
            throw new EntityNotFoundException("Not found Job by id: " + id);
        }
        jobRepository.deleteById(id);
    }

    public List <JobWithCompanyAndReviewDTO> companyBreakerFallback (Exception e) {
        throw new ServiceUnavailableException("The service is temporarily not available, please try again later");
    }

    public JobWithCompanyAndReviewDTO companyBreakerFallback (Long id, Exception e) {
        throw new ServiceUnavailableException("The service is temporarily not available, please try again later");
    }

    public List <JobWithCompanyAndReviewDTO> companyBreakerFallback (RequestNotPermitted e) {
        throw new LimitRequestException("Too many requests. Try again later.");
    }

    public JobWithCompanyAndReviewDTO companyBreakerFallback (Long id, RequestNotPermitted e) {
        throw new LimitRequestException("Too many requests. Try again later.");
    }

}
