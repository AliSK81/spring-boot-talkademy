package com.alisk.lms.service;

import com.alisk.lms.model.Professor;
import com.alisk.lms.model.StudentCourse;

public interface ProfessorService {

    Professor addProfessor(Professor professor);

    Professor acceptCourse(Integer professorId, Integer courseId);

    StudentCourse scoreStudent(Integer professorId, Integer studentId, Double score);

}
