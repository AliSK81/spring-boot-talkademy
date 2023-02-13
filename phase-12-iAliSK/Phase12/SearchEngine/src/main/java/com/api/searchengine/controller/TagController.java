package com.api.searchengine.controller;

import com.api.searchengine.model.response.TagGetAllResponse;
import com.api.searchengine.service.TagService;
import com.api.searchengine.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagController {
    private final TagService tagService;

    @GetMapping("/tags/all")
    public ResponseEntity<List<TagGetAllResponse>> getTags() {
        return ResponseEntity.ok(
                Mapper.mapAll(tagService.getTags(), TagGetAllResponse.class)
        );
    }
}
