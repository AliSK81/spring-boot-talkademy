package com.alisk.lms.controller;

import com.alisk.lms.model.Student;
import com.alisk.lms.model.request.StudentAddRequest;
import com.alisk.lms.service.StudentService;
import com.alisk.lms.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@AllArgsConstructor
@RestController
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/students")
    public ResponseEntity<Object> addStudent(@RequestBody StudentAddRequest request) {

        Student entity = Mapper.map(request, Student.class);
        Student savedStudent = studentService.addStudent(entity);
        int studentId = savedStudent.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studentId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/students/{studentId}/favorite/{courseId}")
    public ResponseEntity<Object> changeFave(@PathVariable Integer studentId,
                                             @PathVariable Integer courseId) {
        studentService.changeFave(studentId, courseId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/students")
    public ResponseEntity<Set<Student>> getStudents(@RequestParam Double minGPA) {

        Set<Student> students = studentService.getStudents(minGPA);

        return ResponseEntity.ok(students);
    }

    @PatchMapping("/students/{studentId}/register/{courseId}")
    public ResponseEntity<Object> regCourse(@PathVariable Integer studentId,
                                            @PathVariable Integer courseId) {
        studentService.regCourse(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/students/{studentId}/delete/{courseId}")
    public ResponseEntity<Void> delCourse(@PathVariable Integer studentId,
                                          @PathVariable Integer courseId) {
        studentService.delCourse(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

}
