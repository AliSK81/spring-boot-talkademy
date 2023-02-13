package com.api.searchengine.repository.elastic;

import com.api.searchengine.model.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Set;

public interface ESCommentRepository extends ElasticsearchRepository<Comment, Integer> {
    Set<Comment> findAllByPostId(Integer postId);
}