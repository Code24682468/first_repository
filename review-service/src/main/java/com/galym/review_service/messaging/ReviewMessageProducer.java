package com.galym.review_service.messaging;

import com.galym.review_service.dto.ReviewRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ReviewMessageProducer (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage (ReviewRequest reviewRequest) {
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewRequest);
    }

}
