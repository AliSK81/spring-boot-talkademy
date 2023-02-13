package com.kafka.service;

import com.kafka.model.Basics;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;

@RequiredArgsConstructor
@Service
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
    private final KafkaTemplate<String, Basics> kafkaTemplate;

    @Value(value = "${kafka.topic.name}")
    private String kafkaTopic;

    @Value(value = "${resource.location}")
    private String resLoc;

    private static Integer maxRecords = 1000;

    public void sendMessages() {

        try (BufferedReader TSVReader = new BufferedReader(new FileReader(ResourceUtils.getFile(resLoc)))) {

            int records = 0;

            String line;
            while ((line = TSVReader.readLine()) != null) {
                String[] L = line.split("\t");

                Basics entity = Basics.builder()
                        .nconst(L[0])
                        .primaryName(L[1])
                        .birthYear(L[2])
                        .deathYear(L[3])
                        .primaryProfession(L[4].split(","))
                        .knownForTitles(L[5].split(","))
                        .build();

                kafkaTemplate.send(kafkaTopic, entity);

                if(++records == maxRecords) break;
            }
            logger.info("producer sent all messages successfully.");

        } catch (Exception e) {
            logger.error("error in producer service: " + e.getMessage());
        }

    }
}
