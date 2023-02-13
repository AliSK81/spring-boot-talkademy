package com.alisk.lms.service;

import com.alisk.lms.model.Course;
import com.alisk.lms.model.Student;
import com.alisk.lms.model.StudentCourse;
import com.alisk.lms.model.StudentCourseId;
import com.alisk.lms.repository.CourseRepository;
import com.alisk.lms.repository.StudentCourseRepository;
import com.alisk.lms.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final StudentCourseRepository studentCourseRepo;


    @Override
    public Student addStudent(Student student) {
        return studentRepo.save(student);
    }

    @Override
    public Student changeFave(Integer studentId, Integer courseId) {
        Student student = getStudent(studentId);
        Course course = getCourse(courseId);
        student.setFavCourse(course);
        studentRepo.save(student);
        return student;
    }

    @Override
    public Set<Student> getStudents(Double minGPA) {
        Iterable<Student> iterable = studentRepo.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .filter(student -> student.getGPA() >= minGPA)
                .collect(Collectors.toSet());
    }

    @Override
    public StudentCourse regCourse(Integer studentId, Integer courseId) {
        Student student = getStudent(studentId);
        Course course = getCourse(courseId);

        StudentCourse sc = StudentCourse.builder()
                .student(student)
                .course(course)
                .build();

        return studentCourseRepo.save(sc);
    }

    @Override
    public void delCourse(Integer studentId, Integer courseId) {
        StudentCourseId id = StudentCourseId.builder()
                .studentId(studentId).courseId(courseId).build();
        studentCourseRepo.deleteById(id);
    }

    private Student getStudent(Integer id) {
        return studentRepo.findById(id).orElseThrow(RuntimeException::new);
    }

    private Course getCourse(Integer id) {
        return courseRepo.findById(id).orElseThrow(RuntimeException::new);
    }
}
