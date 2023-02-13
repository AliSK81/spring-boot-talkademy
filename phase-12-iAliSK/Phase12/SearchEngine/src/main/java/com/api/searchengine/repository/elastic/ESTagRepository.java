package com.api.searchengine.repository.elastic;

import com.api.searchengine.model.Tag;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ESTagRepository extends ElasticsearchRepository<Tag, Integer> {
    List<Tag> findAll();

    Tag findByTagName(String tgName);
}