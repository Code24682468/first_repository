package com.galym.job_service.service;

import com.galym.job_service.dto.JobRequest;
import com.galym.job_service.dto.JobResponse;
import com.galym.job_service.dto.JobSearchFilter;
import com.galym.job_service.dto.JobWithCompanyAndReviewDTO;

import java.util.List;

public interface JobService {

    List <JobWithCompanyAndReviewDTO> getAllJobs (JobSearchFilter filter);

    JobWithCompanyAndReviewDTO getJob (Long id);

    JobResponse createJob (JobRequest jobRequest);

    JobResponse updateJob (Long id, JobRequest jobRequest);

    void deleteJob (Long id);

}
