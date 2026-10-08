package com.galym.job_service.controller;

import com.galym.job_service.dto.JobRequest;
import com.galym.job_service.dto.JobResponse;
import com.galym.job_service.dto.JobSearchFilter;
import com.galym.job_service.dto.JobWithCompanyAndReviewDTO;
import com.galym.job_service.service.JobService;
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
public class JobController {

    private final JobService jobService;
    private final Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    public JobController (JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity <List<JobWithCompanyAndReviewDTO>> getAllJobs (
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "minSalary", required = false) BigDecimal minSalary,
            @RequestParam(name = "maxSalary", required = false) BigDecimal maxSalary,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber
    ) {
        log.info("Called getAllJobs");
        var filter = new JobSearchFilter(
                title,
                minSalary,
                maxSalary,
                location,
                companyId,
                pageSize,
                pageNumber
        );
        return ResponseEntity.ok(jobService.getAllJobs(filter));
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity <JobWithCompanyAndReviewDTO> getJob (@PathVariable("id") Long id) {
        log.info("Called getJob");
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getJob(id));
    }

    @PostMapping("/jobs")
    public ResponseEntity <JobResponse> createJob (@RequestBody @Valid JobRequest jobRequest) {
        log.info("Called createJob");
        return new ResponseEntity <> (jobService.createJob(jobRequest), HttpStatus.CREATED);
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity <JobResponse> updateJob (@PathVariable Long id, @RequestBody @Valid JobRequest request) {
        log.info("Called updateJob");
        return ResponseEntity.status(HttpStatus.OK).body(jobService.updateJob(id, request));
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity <String> deleteJob (@PathVariable Long id) {
        log.info("Called deleteJob");
        jobService.deleteJob(id);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted job with id = " + id);
    }

}
