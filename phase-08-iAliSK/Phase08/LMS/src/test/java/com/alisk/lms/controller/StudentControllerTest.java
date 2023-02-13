package com.alisk.lms.controller;

import com.alisk.lms.model.Student;
import com.alisk.lms.service.StudentService;
import com.alisk.lms.util.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void addStudentTest() throws Exception {

        int studentId = 1;

        Student entityModel = Student.builder().fName("Ali").lName("Eb").build();
        Student savedStudent = Student.builder().id(studentId).fName("Ali").lName("Eb").build();
        when(studentService.addStudent(any(Student.class))).thenReturn(savedStudent);

        String uri = "/students";
        String exceptedLocation = String.format("http://localhost/students/%d", studentId);

        String json = Mapper.mapToJson(entityModel);

        mvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", exceptedLocation));

    }

    @Test
    public void changeFaveTest() throws Exception {

        int studentId = 2;
        int courseId = 3;

        doReturn(null).when(studentService).changeFave(studentId, courseId);

        String uri = String.format("/students/%d/favorite/%d", studentId, courseId);

        mvc.perform(MockMvcRequestBuilders
                        .patch(uri))
                .andExpect(status().isOk());

    }

    @Test
    public void getStudentsTest() throws Exception {

        Set<Student> students = new HashSet<>(List.of(
                Student.builder().id(1).build(),
                Student.builder().id(2).build()
        ));

        double minGPA = 15;
        when(studentService.getStudents(minGPA)).thenReturn(students);

        String uri = "/students";

        String json = Mapper.mapToJson(students);

        mvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .param("minGPA", Double.toString(minGPA))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(json));

    }
}