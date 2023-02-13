package com.api.searchengine.service;

import com.api.searchengine.model.Post;
import com.api.searchengine.model.request.PostSearchRequest;
import com.api.searchengine.model.response.CommentDto;
import com.api.searchengine.model.response.PostSearchResponse;
import com.api.searchengine.repository.elastic.ESCommentRepository;
import com.api.searchengine.repository.elastic.ESPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final ESPostRepository esPostRepo;

    private final ESCommentRepository esCommentRepo;


    public List<PostSearchResponse> search(PostSearchRequest request) {

        List<PostSearchResponse> responses = new ArrayList<>();

        for (Post post : esPostRepo.findAll()) {

            if (post.getTitle() != null && !post.getTitle().contains(request.getKw())) continue;
            if (request.getFrom_date().isAfter(post.getCreationDate())) continue;
            if (!tagsMatch(post, request)) continue;
            if (post.getScore() < request.getMin_score()) continue;

            PostSearchResponse searchResult = PostSearchResponse.builder()
                    .orig_post_id(post.getId())
                    .orig_post_title(post.getTitle())
                    .orig_post_body(post.getBody())
                    .orig_post_score(post.getScore())
                    .orig_post_comments(esCommentRepo.findAllByPostId(post.getId())
                            .stream().map(CommentDto::new).collect(Collectors.toSet()))
                    .build();

            Post bestAnswer = getPost(post.getAcceptedAnswerId());

            if (bestAnswer != null) {
                searchResult.setBest_ans_id(bestAnswer.getId());
                searchResult.setBest_ans_body(bestAnswer.getBody());
                searchResult.setBest_ans_score(bestAnswer.getScore());
            }

            responses.add(searchResult);
        }

        return responses;
    }

    private Post getPost(Integer postId) {
        return esPostRepo.findById(postId).orElse(null);
    }

    private List<String> splitTags(String tags) {
        return List.of(tags.replaceAll("<", "").split(">"));
    }

    private boolean tagsMatch(Post post, PostSearchRequest request) {
        for (String tgName : splitTags(post.getTags())) {
            if (!request.getT_names().contains(tgName)) return false;
        }
        return true;
    }

}
