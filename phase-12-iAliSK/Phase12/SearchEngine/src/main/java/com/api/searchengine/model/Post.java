package com.api.searchengine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    @Id
    private Integer id;
    private Integer postTypeId;
    private Integer parentId;
    private Integer acceptedAnswerId;
    private LocalDateTime creationDate;
    private Integer score;
    private Integer viewCount;
    private String body;
    private Integer ownerUserId;
    private String ownerDisplayName;
    private Integer lastEditorUserId;
    private String lastEditorDisplayName;
    private LocalDateTime lastEditDate;
    private LocalDateTime lastActivityDate;
    private String title;

    @Transient
    @JsonProperty("Tags")
    private String tags;

//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(name = "post_tag",
//            joinColumns = @JoinColumn(name = "post_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
//    private Set<Tag> tags;

    private Integer answerCount;
    private Integer commentCount;
    private Integer favoriteCount;
    private LocalDateTime closedDate;
    private LocalDateTime communityOwnedDate;
    private String contentLicense;

}
