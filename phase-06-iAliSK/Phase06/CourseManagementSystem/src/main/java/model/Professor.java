package model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class Professor {
    @Setter(AccessLevel.NONE)
    private int id;
    private String fName;
    private String lName;
}
