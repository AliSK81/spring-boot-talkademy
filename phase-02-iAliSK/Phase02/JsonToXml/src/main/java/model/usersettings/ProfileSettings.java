package model.usersettings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ProfileSettings {
    private int userType;
    private int userStatus;
    private String profilePicture;
    private String coverPicture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
