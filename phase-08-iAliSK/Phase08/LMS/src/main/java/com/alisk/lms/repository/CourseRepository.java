package com.alisk.lms.repository;

import com.alisk.lms.model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer> {
}