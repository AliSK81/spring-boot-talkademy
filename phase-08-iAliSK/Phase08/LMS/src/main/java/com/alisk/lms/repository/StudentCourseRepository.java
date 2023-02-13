package com.alisk.lms.repository;

import com.alisk.lms.model.StudentCourse;
import com.alisk.lms.model.StudentCourseId;
import org.springframework.data.repository.CrudRepository;

public interface StudentCourseRepository extends CrudRepository<StudentCourse, StudentCourseId> {
}