package com.api.searchengine.repository.mysql;

import com.api.searchengine.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface MSCommentRepository extends CrudRepository<Comment, Integer> {
}