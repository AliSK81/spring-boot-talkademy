package com.alisk.lms.service;

import com.alisk.lms.model.Course;
import com.alisk.lms.model.Professor;
import com.alisk.lms.model.StudentCourse;
import com.alisk.lms.repository.CourseRepository;
import com.alisk.lms.repository.ProfessorRepository;
import com.alisk.lms.repository.StudentCourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepo;
    private final CourseRepository courseRepo;
    private final StudentCourseRepository studentCourseRepo;

    @Override
    public Professor addProfessor(Professor professor) {
        return professorRepo.save(professor);
    }

    @Override
    public Professor acceptCourse(Integer professorId, Integer courseId) {
        Professor professor = getProfessor(professorId);
        Course course = getCourse(courseId);
        professor.getCourses().add(course);
        return professorRepo.save(professor);
    }

    @Override
    public StudentCourse scoreStudent(Integer professorId, Integer studentId, Double score) {
        StudentCourse sc = findStudentCourse(professorId, studentId);
        sc.setScore(score);
        return studentCourseRepo.save(sc);
    }

    private StudentCourse findStudentCourse(Integer professorId, Integer studentId) {
        Professor professor = getProfessor(professorId);
        Set<Course> acceptedCourses = professor.getCourses();

        Stream<StudentCourse> stream = StreamSupport.stream(studentCourseRepo.findAll().spliterator(), false);

        return stream.filter(sc -> {
            boolean sameStudent = studentId.equals(sc.getStudent().getId());
            boolean sameCourse = acceptedCourses.contains(sc.getCourse());
            return sameStudent && sameCourse;
        }).findAny().orElseThrow(RuntimeException::new);
    }

    private Professor getProfessor(Integer id) {
        return professorRepo.findById(id).orElseThrow(RuntimeException::new);
    }

    private Course getCourse(Integer id) {
        return courseRepo.findById(id).orElseThrow(RuntimeException::new);
    }

}
