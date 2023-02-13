package com.alisk.lms.repository;

import com.alisk.lms.model.Student;
import org.springframework.data.repository.CrudRepository;


public interface StudentRepository extends CrudRepository<Student, Integer> {
}