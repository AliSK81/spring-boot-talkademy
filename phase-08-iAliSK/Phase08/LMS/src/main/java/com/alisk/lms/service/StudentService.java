package com.alisk.lms.service;

import com.alisk.lms.model.Student;
import com.alisk.lms.model.StudentCourse;

import java.util.Set;

public interface StudentService {

    Student addStudent(Student student);

    Student changeFave(Integer studentId, Integer courseId);

    Set<Student> getStudents(Double minGPA);

    StudentCourse regCourse(Integer studentId, Integer courseId);

    void delCourse(Integer studentId, Integer courseId);

}
