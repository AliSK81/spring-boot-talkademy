package com.alisk.lms.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;
    @NotNull
    private String fName;
    @NotNull
    private String lName;
    private Boolean qualified;
    private Double GPA;

    @OneToOne
    @JoinColumn(name = "fav_course_id")
    private Course favCourse;

}
