package com.alisk.lms.model.response;

import com.alisk.lms.model.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class StudentRegCourseResponse {
    private Set<Course> courses;
}
