package model.userinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PrivateInfo {
    private int id;
    private String token;
    private String name;
    private int gender;
    private String dob;
    private String about;
    private String email;
    private String password;
}
