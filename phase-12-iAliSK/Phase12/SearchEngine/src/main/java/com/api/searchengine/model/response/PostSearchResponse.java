package com.api.searchengine.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchResponse {
    private Integer orig_post_id;
    private String orig_post_title;
    private String orig_post_body;
    private Integer orig_post_score;
    private Set<CommentDto> orig_post_comments;
    private Integer best_ans_id;
    private String best_ans_body;
    private Integer best_ans_score;
}
