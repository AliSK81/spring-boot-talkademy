package com.api.searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Comment {
    @Id
    private Integer id;
    private Integer postId;
    private Integer score;
    private String text;
    private LocalDateTime creationDate;
    private Integer userId;
    private String userDisplayName;
    private String contentLicense;

}
