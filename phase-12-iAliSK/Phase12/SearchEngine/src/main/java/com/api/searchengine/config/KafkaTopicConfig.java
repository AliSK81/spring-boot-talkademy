package com.api.searchengine.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${kafka.topic.tag.name}")
    private String tagTopicName;

    @Value(value = "${kafka.topic.comment.name}")
    private String commentTopicName;

    @Value(value = "${kafka.topic.post.name}")
    private String postTopicName;

    @Bean
    public NewTopic tagTopic() {
        return TopicBuilder.name(tagTopicName)
                .partitions(5)
                .config(TopicConfig.RETENTION_MS_CONFIG, "3600000")
                .build();
    }

    @Bean
    public NewTopic commentTopic() {
        return TopicBuilder.name(commentTopicName)
                .partitions(5)
                .config(TopicConfig.RETENTION_MS_CONFIG, "3600000")
                .build();
    }

    @Bean
    public NewTopic postTopic() {
        return TopicBuilder.name(postTopicName)
                .partitions(5)
                .config(TopicConfig.RETENTION_MS_CONFIG, "3600000")
                .build();
    }
}
