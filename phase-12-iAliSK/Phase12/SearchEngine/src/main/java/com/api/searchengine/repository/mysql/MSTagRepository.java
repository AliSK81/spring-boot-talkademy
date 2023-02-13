package com.api.searchengine.repository.mysql;

import com.api.searchengine.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MSTagRepository extends CrudRepository<Tag, String> {
    List<Tag> findAll();

    Tag findByTagName(String tgName);
}