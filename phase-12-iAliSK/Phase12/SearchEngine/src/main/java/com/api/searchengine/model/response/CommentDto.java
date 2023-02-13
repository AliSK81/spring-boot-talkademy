package com.api.searchengine.model.response;

import com.api.searchengine.model.Comment;
import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link com.api.searchengine.model.Comment} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CommentDto implements Serializable {
    private String u_name;
    private String body;

    public CommentDto(Comment comment) {
        this.u_name = comment.getUserDisplayName();
        this.body = comment.getText();
    }
}