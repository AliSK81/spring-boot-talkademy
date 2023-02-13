package com.alisk.lms.service;

import com.alisk.lms.model.Course;
import com.alisk.lms.model.Student;
import com.alisk.lms.model.StudentCourse;
import com.alisk.lms.repository.CourseRepository;
import com.alisk.lms.repository.StudentCourseRepository;
import com.alisk.lms.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private StudentCourseRepository studentCourseRepo;

    @Test
    void addCourseTest() {

        Course entityModel = Course.builder().build();
        Course savedCourse = Course.builder().id(1).build();
        when(courseRepo.save(any(Course.class))).thenReturn(savedCourse);

        Course actual = courseService.addCourse(entityModel);

        verify(courseRepo).save(any(Course.class));
        assertEquals(savedCourse, actual);

    }

    @Test
    void getAvgScoreTest() {

        Course course1 = Course.builder().id(1).build();
        Course course2 = Course.builder().id(2).build();

        when(studentCourseRepo.findAll()).thenReturn(List.of(
                StudentCourse.builder().course(course1).score(10.0).build(),
                StudentCourse.builder().course(course1).score(20.0).build(),
                StudentCourse.builder().course(course2).score(20.0).build()
        ));

        double avg = courseService.getAvgScore(course1.getId());

        assertEquals(15.0, avg);
    }

    @Test
    void getCoursesPopularityTest() {

        Course course1 = Course.builder().id(1).build();
        Course course2 = Course.builder().id(2).build();

        when(studentRepo.findAll()).thenReturn(List.of(
                Student.builder().favCourse(course1).build(),
                Student.builder().favCourse(course1).build(),
                Student.builder().favCourse(course2).build()
        ));

        Map<Integer, Integer> popularity = courseService.getCoursesPopularity();

        assertEquals(2, popularity.get(course1.getId()));
        assertEquals(1, popularity.get(course2.getId()));

    }
}