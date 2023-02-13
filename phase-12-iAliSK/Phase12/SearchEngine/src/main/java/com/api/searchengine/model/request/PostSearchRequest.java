package com.api.searchengine.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchRequest {
    private String kw;
    private LocalDateTime from_date;
    private Set<String> t_names;
    private Integer min_score;
    private Integer cnt_in_page;
    private Integer page_no;
}
