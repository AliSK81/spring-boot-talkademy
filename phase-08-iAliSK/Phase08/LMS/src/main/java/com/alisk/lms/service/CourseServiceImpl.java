package com.alisk.lms.service;

import com.alisk.lms.model.Course;
import com.alisk.lms.model.Student;
import com.alisk.lms.model.StudentCourse;
import com.alisk.lms.repository.CourseRepository;
import com.alisk.lms.repository.StudentCourseRepository;
import com.alisk.lms.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;
    private final StudentCourseRepository studentCourseRepo;


    @Override
    public Course addCourse(Course course) {
        return courseRepo.save(course);
    }

    @Override
    public Double getAvgScore(Integer courseId) {
        Stream<StudentCourse> stream = StreamSupport.stream(studentCourseRepo.findAll().spliterator(), false);

        return stream.filter(sc -> sc.getCourse().getId().equals(courseId))
                .collect(Collectors.averagingDouble(StudentCourse::getScore));
    }

    @Override
    public HashMap<Integer, Integer> getCoursesPopularity() {
        Iterable<Student> students = studentRepo.findAll();

        HashMap<Integer, Integer> map = new HashMap<>();

        students.forEach(s -> {
            Course fave = s.getFavCourse();
            if (fave != null) {
                map.putIfAbsent(fave.getId(), 0);
                map.merge(fave.getId(), 1, Integer::sum);
            }
        });

        return map;
    }
}
