package model.usersettings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccountSettings {
    private boolean enablefollowme;
    private boolean sendmenotifications;
    private boolean sendTextmessages;
    private boolean enabletagging;
}
