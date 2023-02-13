package com.api.searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Tag {
    @Id
    private Integer id;
    private String tagName;
    private Integer count;
    private Integer excerptPostId;
    private Integer wikiPostId;
}
