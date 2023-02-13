package com.kafka.controller;

import com.kafka.service.ProducerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ProducerController {

    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    private final ProducerService senderService;

    @GetMapping("/publish")
    public ResponseEntity<Object> sendMessage() {
        logger.info("producer controller called its service.");
        senderService.sendMessages();
        return ResponseEntity.ok("published");
    }
}
