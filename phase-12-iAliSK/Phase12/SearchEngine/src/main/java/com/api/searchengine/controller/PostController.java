package com.api.searchengine.controller;

import com.api.searchengine.model.request.PostSearchRequest;
import com.api.searchengine.model.response.PostSearchResponse;
import com.api.searchengine.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostSearchResponse>> addPost(@RequestBody PostSearchRequest request) {

        return ResponseEntity.ok(
                postService.search(request)
        );
    }
}
