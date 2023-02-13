package com.api.searchengine.repository.mysql;

import com.api.searchengine.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface MSPostRepository extends CrudRepository<Post, String> {
}