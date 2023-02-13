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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    @NotNull
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer capacity;
}
