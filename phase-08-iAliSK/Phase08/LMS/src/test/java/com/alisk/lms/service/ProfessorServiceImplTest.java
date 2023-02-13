package com.alisk.lms.service;

import com.alisk.lms.model.Course;
import com.alisk.lms.model.Professor;
import com.alisk.lms.model.Student;
import com.alisk.lms.model.StudentCourse;
import com.alisk.lms.repository.CourseRepository;
import com.alisk.lms.repository.ProfessorRepository;
import com.alisk.lms.repository.StudentCourseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProfessorServiceImplTest {

    @InjectMocks
    private ProfessorServiceImpl professorService;

    @Mock
    private ProfessorRepository professorRepo;

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private StudentCourseRepository studentCourseRepo;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addProfessorTest() {
        Professor entityModel = Professor.builder().build();
        Professor savedProfessor = Professor.builder().id(1).build();
        when(professorRepo.save(any(Professor.class))).thenReturn(savedProfessor);

        Professor actual = professorService.addProfessor(entityModel);

        verify(professorRepo).save(any(Professor.class));
        assertEquals(savedProfessor, actual);
    }

    @Test
    void acceptCourseTest() {

        Course course = Course.builder().id(1).build();
        Professor professor = Professor.builder().id(2).courses(new HashSet<>()).build();

        when(professorRepo.findById(professor.getId())).thenReturn(Optional.of(professor));
        when(courseRepo.findById(course.getId())).thenReturn(Optional.of(course));
        when(professorRepo.save(any(Professor.class))).thenAnswer(i -> i.getArgument(0));

        Professor updatedProfessor = professorService
                .acceptCourse(professor.getId(), course.getId());

        verify(professorRepo).save(updatedProfessor);
        assertTrue(updatedProfessor.getCourses().contains(course));

    }

    @Test
    void scoreStudentTest() {

        Student student1 = Student.builder().id(1).build();
        Student student2 = Student.builder().id(2).build();

        Course course1 = Course.builder().id(1).build();
        Course course2 = Course.builder().id(2).build();

        Professor professor = Professor.builder().id(1).build();

        professor.setCourses(new HashSet<>(List.of(
                course1, course2
        )));

        when(professorRepo.findById(professor.getId())).thenReturn(Optional.of(professor));

        when(studentCourseRepo.findAll()).thenReturn(List.of(
                StudentCourse.builder().student(student1).course(course1).build(),
                StudentCourse.builder().student(student2).course(course1).build()
        ));

        when(studentCourseRepo.save(any(StudentCourse.class))).thenAnswer(i -> i.getArgument(0));

        StudentCourse updatedSC = professorService
                .scoreStudent(professor.getId(), student1.getId(), 10.0);

        verify(studentCourseRepo).save(updatedSC);
        assertEquals(10.0, updatedSC.getScore());

    }
}