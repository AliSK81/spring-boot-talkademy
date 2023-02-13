package com.alisk.lms.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "professor_id")
    @NotNull
    private Integer id;
    @NotNull
    private String fName;
    @NotNull
    private String lName;

    @ManyToMany
    @JoinTable(name = "section",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;
}
