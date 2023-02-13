package com.alisk.lms.service;

import com.alisk.lms.model.Course;

import java.util.HashMap;

public interface CourseService {

    Course addCourse(Course course);

    Double getAvgScore(Integer courseId);

    HashMap<Integer, Integer> getCoursesPopularity();
}
