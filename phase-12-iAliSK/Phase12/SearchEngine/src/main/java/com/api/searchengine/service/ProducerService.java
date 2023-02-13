package com.api.searchengine.service;

import com.api.searchengine.model.Comment;
import com.api.searchengine.model.Post;
import com.api.searchengine.model.Tag;
import com.api.searchengine.util.XmlReader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Service
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
    private final KafkaTemplate<String, Tag> tagTemplate;

    private final KafkaTemplate<String, Comment> commentTemplate;

    private final KafkaTemplate<String, Post> postTemplate;

    @Value(value = "${kafka.topic.tag.name}")
    private String tagTopicName;

    @Value(value = "${kafka.topic.comment.name}")
    private String commentTopicName;

    @Value(value = "${kafka.topic.post.name}")
    private String postTopicName;
    @Value(value = "${resource.location}")
    private String resLoc;


//    @PostConstruct
    public void saveTags() {
        XmlReader.read(resLoc + "Tags.xml", Tag.class)
                .forEach(tag -> tagTemplate.send(tagTopicName, tag));
        logger.info("tags saved.");
    }

//    @PostConstruct
    public void saveComments() {
        XmlReader.read(resLoc + "Comments.xml", Comment.class)
                .forEach(comment -> commentTemplate.send(commentTopicName, comment));
        logger.info("comments saved.");
    }

//    @PostConstruct
    public void savePosts() {
        XmlReader.read(resLoc + "Posts.xml", Post.class)
                .forEach(post -> postTemplate.send(postTopicName, post));
        logger.info("posts saved.");
    }
}
