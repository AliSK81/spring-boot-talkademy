package com.api.searchengine.controller;

import com.api.searchengine.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProducerController {

    private final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    private final ProducerService producerService;

    @GetMapping("/preprocess")
    public ResponseEntity<Object> publish() {

        producerService.saveTags();
        logger.info("tags loaded");
        producerService.saveComments();
        logger.info("comments loaded");
        producerService.savePosts();
        logger.info("posts loaded");

        return ResponseEntity.ok("done");
    }
}
