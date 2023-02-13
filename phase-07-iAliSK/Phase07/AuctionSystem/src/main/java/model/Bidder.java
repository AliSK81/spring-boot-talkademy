package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bidder {
    private String id;
    private String name;
    private Long budget;
}
