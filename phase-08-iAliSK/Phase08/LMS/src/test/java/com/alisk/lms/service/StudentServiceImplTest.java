package com.alisk.lms.service;

import com.alisk.lms.model.Course;
import com.alisk.lms.model.Student;
import com.alisk.lms.model.StudentCourse;
import com.alisk.lms.model.StudentCourseId;
import com.alisk.lms.repository.CourseRepository;
import com.alisk.lms.repository.StudentCourseRepository;
import com.alisk.lms.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private StudentCourseRepository studentCourseRepo;

    @Test
    public void addStudentTest() {

        Student entityModel = Student.builder().build();
        Student savedStudent = Student.builder().id(1).build();
        when(studentRepo.save(any(Student.class))).thenReturn(savedStudent);

        Student actual = studentService.addStudent(entityModel);

        verify(studentRepo).save(any(Student.class));
        assertEquals(savedStudent, actual);

    }

    @Test
    void changeFaveTest() {

        Course course = Course.builder().id(1).build();
        Student student = Student.builder().id(1).build();

        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepo.findById(course.getId())).thenReturn(Optional.of(course));

        Student updatedStudent = studentService.changeFave(student.getId(), course.getId());

        assertEquals(course, updatedStudent.getFavCourse());
        verify(studentRepo).save(any(Student.class));

    }

    @Test
    void getStudentsTest() {

        double minGPA = 10.5;

        when(studentRepo.findAll()).thenReturn(List.of(
                Student.builder().GPA(10.0).build(),
                Student.builder().GPA(15.0).build(),
                Student.builder().GPA(20.0).build()
        ));

        Set<Student> students = studentService.getStudents(minGPA);

        assertEquals(2, students.size());

    }

    @Test
    void regCourseTest() {
        Course course = Course.builder().id(1).build();
        Student student = Student.builder().id(2).build();

        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepo.findById(course.getId())).thenReturn(Optional.of(course));
        when(studentCourseRepo.save(any(StudentCourse.class))).thenAnswer(i -> i.getArgument(0));

        StudentCourse sc = studentService.regCourse(student.getId(), course.getId());

        verify(studentCourseRepo).save(sc);
        assertEquals(course, sc.getCourse());
        assertEquals(student, sc.getStudent());

    }

    @Test
    void delCourseTest() {
        int studentId = 1;
        int courseId = 1;

        StudentCourseId id = StudentCourseId.builder()
                .studentId(studentId).courseId(courseId).build();

        studentService.delCourse(studentId, courseId);
        verify(studentCourseRepo).deleteById(id);
    }
}