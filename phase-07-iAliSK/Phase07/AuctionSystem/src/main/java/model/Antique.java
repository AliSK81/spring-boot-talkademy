package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Antique {
    private String id;
    private String name;
    private Long startPrice;
}
