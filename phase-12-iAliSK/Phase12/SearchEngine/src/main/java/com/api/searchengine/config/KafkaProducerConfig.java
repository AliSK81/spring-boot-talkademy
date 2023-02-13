package com.api.searchengine.config;


import com.api.searchengine.model.Comment;
import com.api.searchengine.model.Post;
import com.api.searchengine.model.Tag;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {
    @Value(value = "${kafka.bootstrap-address}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, Tag> tagKafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProperties());
    }

    @Bean
    public ProducerFactory<String, Comment> commentKafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProperties());
    }

    @Bean
    public ProducerFactory<String, Post> postKafkaProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getProperties());
    }

    @Bean
    public KafkaTemplate<String, Tag> tagKafkaTemplate() {
        return new KafkaTemplate<>(tagKafkaProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, Comment> commentKafkaTemplate() {
        return new KafkaTemplate<>(commentKafkaProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, Post> postKafkaTemplate() {
        return new KafkaTemplate<>(postKafkaProducerFactory());
    }


    private Map<String, Object> getProperties() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }
}