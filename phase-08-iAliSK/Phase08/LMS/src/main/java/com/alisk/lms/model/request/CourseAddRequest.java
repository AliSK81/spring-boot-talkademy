package com.alisk.lms.model.request;

import lombok.Data;

@Data
public class CourseAddRequest {
    private final String name;
    private final Integer capacity;
}
