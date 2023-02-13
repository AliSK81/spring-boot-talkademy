package controller;

import com.fasterxml.jackson.core.JsonGenerator;
import model.Employee;
import model.userinfo.LocationInfo;
import model.userinfo.PaymentInfo;
import model.userinfo.PrivateInfo;
import model.usersettings.AccountSettings;
import model.usersettings.ProfileSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeSerializerTest {

    private EmployeeSerializer employeeSerializer;
    private JsonGenerator jsonGenerator;

    @BeforeEach
    void setUp() {
        employeeSerializer = new EmployeeSerializer();
        jsonGenerator = mock(JsonGenerator.class);
    }

    @Test
    void testSerialize() throws IOException {
        Employee employee = buildEmployee();
        employeeSerializer.serialize(employee, jsonGenerator, null);
        verify(jsonGenerator).writeNumberField("id", 1000);
        verify(jsonGenerator).writeStringField("token", "test-token");
        verify(jsonGenerator).writeBooleanField("enablefollowme", true);
    }

    private Employee buildEmployee() {
        PaymentInfo paymentInfo = PaymentInfo.builder().build();
        PrivateInfo privateInfo = PrivateInfo.builder().id(1000).token("test-token").build();
        LocationInfo locationInfo = LocationInfo.builder().build();
        ProfileSettings profileSettings = ProfileSettings.builder().build();
        AccountSettings accountSettings = AccountSettings.builder().enablefollowme(true).build();

        return Employee.builder()
                .paymentInfo(paymentInfo)
                .privateInfo(privateInfo)
                .locationInfo(locationInfo)
                .profileSettings(profileSettings)
                .accountSettings(accountSettings)
                .build();
    }
}
