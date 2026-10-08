package com.galym.company_service.messaging;

import com.galym.company_service.dto.ReviewRequest;
import com.galym.company_service.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {

    private final CompanyService companyService;

    @Autowired
    public ReviewMessageConsumer (CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumerMessage (ReviewRequest reviewRequest) {
        companyService.updateCompanyRating(reviewRequest);
    }

}
