package com.galym.job_service.mapper;

import com.galym.job_service.dto.JobRequest;
import com.galym.job_service.dto.JobResponse;
import com.galym.job_service.dto.JobWithCompanyAndReviewDTO;
import com.galym.job_service.external.Company;
import com.galym.job_service.external.Review;
import com.galym.job_service.model.Job;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobMapper {

    public JobResponse toResponse (Job job) {
        JobResponse jobResponse = new JobResponse ();
        jobResponse.setId(job.getId());
        jobResponse.setTitle(job.getTitle());
        jobResponse.setDescription(job.getDescription());
        jobResponse.setMinSalary(job.getMinSalary());
        jobResponse.setMaxSalary(job.getMaxSalary());
        jobResponse.setLocation(job.getLocation());
        jobResponse.setCompanyId(job.getCompanyId());
        return jobResponse;
    }

    public Job toJob (JobRequest jobRequest) {
        Job job = new Job ();
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setMinSalary(jobRequest.getMinSalary());
        job.setMaxSalary(jobRequest.getMaxSalary());
        job.setLocation(jobRequest.getLocation());
        job.setCompanyId(jobRequest.getCompanyId());
        return job;
    }

    public JobWithCompanyAndReviewDTO toJobWithCompanyAndReviewDTO (
            Job job,
            Company company,
            List <Review> reviewList
    ) {
        JobWithCompanyAndReviewDTO jobWithCompanyAndReviewDTO = new JobWithCompanyAndReviewDTO();
        jobWithCompanyAndReviewDTO.setId(job.getId());
        jobWithCompanyAndReviewDTO.setTitle(job.getTitle());
        jobWithCompanyAndReviewDTO.setDescription(job.getDescription());
        jobWithCompanyAndReviewDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyAndReviewDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyAndReviewDTO.setLocation(job.getLocation());
        jobWithCompanyAndReviewDTO.setCompany(company);
        jobWithCompanyAndReviewDTO.setReviews(reviewList);
        return jobWithCompanyAndReviewDTO;
    }

}
