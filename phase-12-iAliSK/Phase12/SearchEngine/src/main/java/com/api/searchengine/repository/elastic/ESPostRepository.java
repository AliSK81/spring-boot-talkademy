package com.api.searchengine.repository.elastic;

import com.api.searchengine.model.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESPostRepository extends ElasticsearchRepository<Post, Integer> {
}