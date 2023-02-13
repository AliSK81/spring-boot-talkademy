package com.alisk.lms.controller;

import com.alisk.lms.model.Course;
import com.alisk.lms.model.request.CourseAddRequest;
import com.alisk.lms.service.CourseService;
import com.alisk.lms.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@AllArgsConstructor
@RestController
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<Object> addCourse(@RequestBody CourseAddRequest request) {

        Course entity = Mapper.map(request, Course.class);
        Course savedCourse = courseService.addCourse(entity);
        int courseId = savedCourse.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(courseId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/courses/{courseId}/average")
    public ResponseEntity<Double> getAvgScore(@PathVariable Integer courseId) {

        double avg = courseService.getAvgScore(courseId);

        return ResponseEntity.ok(avg);
    }

    @GetMapping("/courses/popularity")
    public ResponseEntity<Map<Integer, Integer>> getCoursesPopularity() {

        Map<Integer, Integer> popularity = courseService.getCoursesPopularity();

        return ResponseEntity.ok(popularity);
    }
}
