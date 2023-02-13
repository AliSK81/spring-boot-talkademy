package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.userinfo.LocationInfo;
import model.userinfo.PaymentInfo;
import model.userinfo.PrivateInfo;
import model.usersettings.AccountSettings;
import model.usersettings.ProfileSettings;


@Data
@AllArgsConstructor
@Builder
public class Employee {
    private PrivateInfo privateInfo;
    private LocationInfo locationInfo;
    private PaymentInfo paymentInfo;
    private ProfileSettings profileSettings;
    private AccountSettings accountSettings;
}
