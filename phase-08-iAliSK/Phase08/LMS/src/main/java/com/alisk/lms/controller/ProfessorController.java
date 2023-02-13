package com.alisk.lms.controller;

import com.alisk.lms.model.Professor;
import com.alisk.lms.model.request.ProfessorAddRequest;
import com.alisk.lms.model.request.ProfessorScoreRequest;
import com.alisk.lms.service.ProfessorService;
import com.alisk.lms.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
public class ProfessorController {

    private final ProfessorService professorService;


    @PostMapping("/professors")
    public ResponseEntity<Object> addProfessor(@RequestBody ProfessorAddRequest request) {

        Professor entity = Mapper.map(request, Professor.class);
        Professor savedProfessor = professorService.addProfessor(entity);
        int professorId = savedProfessor.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(professorId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/professors/{professorId}/favorite/{courseId}")
    public ResponseEntity<Object> acceptCourse(@PathVariable Integer professorId,
                                               @PathVariable Integer courseId) {
        professorService.acceptCourse(professorId, courseId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/professors/{professorId}/score/{studentId}")
    public ResponseEntity<Object> scoreStudent(@PathVariable Integer professorId,
                                               @PathVariable Integer studentId,
                                               @RequestBody ProfessorScoreRequest request) {

        professorService.scoreStudent(professorId, studentId, request.getScore());

        return ResponseEntity.ok().build();
    }


}
