package com.kafka.service;

import com.kafka.model.Basics;
import com.kafka.repository.BasicsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final BasicsRepository basicsRepo;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.topic.group-id}")
    public void consume1(Basics entity) {
        logger.info("consume1: " + entity.getNconst());
        basicsRepo.save(entity);
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.topic.group-id}")
    public void consume2(Basics entity) {
        logger.info("consume2: " + entity.getNconst());
        basicsRepo.save(entity);
    }
}
