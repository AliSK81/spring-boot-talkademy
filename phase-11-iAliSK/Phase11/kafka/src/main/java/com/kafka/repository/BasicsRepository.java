package com.kafka.repository;

import com.kafka.model.Basics;
import org.springframework.data.repository.CrudRepository;

public interface BasicsRepository extends CrudRepository<Basics, String> {
}