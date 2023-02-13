package model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class Student {
    @Setter(AccessLevel.NONE)
    private int id;
    private String fName;
    private String lName;
    private Course favCourse;
    private boolean qualified;
    private double GPA;
}
