package com.api.searchengine.config;

import com.api.searchengine.model.Comment;
import com.api.searchengine.model.Post;
import com.api.searchengine.model.Tag;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrap-address}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topic.post.group-id}")
    private String postGroupId;

    @Value(value = "${kafka.topic.comment.group-id}")
    private String commentGroupId;

    @Value(value = "${kafka.topic.tag.group-id}")
    private String tagGroupId;

    @Bean
    public ConsumerFactory<String, Tag> tagKafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getProperties(tagGroupId));
    }

    @Bean
    public ConsumerFactory<String, Comment> commentKafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getProperties(commentGroupId));
    }

    @Bean
    public ConsumerFactory<String, Post> postKafkaConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(getProperties(postGroupId));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Tag> tagKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Tag>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(tagKafkaConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Comment> commentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Comment>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(commentKafkaConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Post> postKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Post>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(postKafkaConsumerFactory());
        return factory;
    }


    private Map<String, Object> getProperties(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }
}