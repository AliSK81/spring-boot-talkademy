package com.api.searchengine.service;


import com.api.searchengine.model.Comment;
import com.api.searchengine.model.Post;
import com.api.searchengine.model.Tag;
import com.api.searchengine.repository.elastic.ESCommentRepository;
import com.api.searchengine.repository.elastic.ESPostRepository;
import com.api.searchengine.repository.elastic.ESTagRepository;
import com.api.searchengine.repository.mysql.MSCommentRepository;
import com.api.searchengine.repository.mysql.MSPostRepository;
import com.api.searchengine.repository.mysql.MSTagRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final MSPostRepository msPostRepo;

    private final MSTagRepository msTagRepo;

    private final MSCommentRepository msCommentRepo;

    private final ESPostRepository esPostRepo;

    private final ESTagRepository esTagRepo;

    private final ESCommentRepository esCommentRepo;


    @KafkaListener(topics = "${kafka.topic.tag.name}", containerFactory = "tagKafkaListenerContainerFactory")
    public void consume(Tag tag) {
        msTagRepo.save(tag);
        esTagRepo.save(tag);
//        logger.info("consume tag ");
    }

    @KafkaListener(topics = "${kafka.topic.comment.name}", containerFactory = "commentKafkaListenerContainerFactory")
    public void consume(Comment comment) {
        msCommentRepo.save(comment);
        esCommentRepo.save(comment);
//        logger.info("consume comment");
    }


    @KafkaListener(topics = "${kafka.topic.post.name}", containerFactory = "postKafkaListenerContainerFactory")
    public void consume(Post post) {
//        findPostTags(post);
        msPostRepo.save(post);
        esPostRepo.save(post);
//        logger.info("consume post");
    }

//    private void findPostTags(Post post) {
//        if (post.getTags() == null) return;
//        String[] tagNames = post.getTags().replaceAll("<", "").split(">");
//        post.setTags(new HashSet<>());
//        for (String tgName : tagNames) {
//            post.getTags().add(msTagRepo.findByTagName(tgName));
//        }
//    }
}
