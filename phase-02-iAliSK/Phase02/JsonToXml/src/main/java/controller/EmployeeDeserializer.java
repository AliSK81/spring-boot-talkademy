package controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.Employee;
import model.userinfo.LocationInfo;
import model.userinfo.PaymentInfo;
import model.userinfo.PrivateInfo;
import model.usersettings.AccountSettings;
import model.usersettings.ProfileSettings;

import java.io.IOException;
import java.time.LocalDateTime;

public class EmployeeDeserializer extends StdDeserializer<Employee> {

    public EmployeeDeserializer() {
        this(null);
    }

    public EmployeeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Employee deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        PrivateInfo privateInfo = PrivateInfo.builder()
                .id(node.get("id").asInt())
                .token(node.get("token").asText())
                .name(node.get("name").asText())
                .gender(node.get("gender").asInt())
                .dob(node.get("dob").asText())
                .about(node.get("about").asText())
                .email(node.get("email").asText())
                .password(node.get("password").asText())
                .build();

        LocationInfo locationInfo = LocationInfo.builder()
                .country(node.get("country").asText())
                .location(node.get("location").asText())
                .lng(node.get("lng").asInt())
                .lat(node.get("lat").asInt())
                .livelng(node.get("livelng").asDouble())
                .livelat(node.get("livelat").asDouble())
                .liveLocation(node.get("liveLocation").asText())
                .build();

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .creditBalance(node.get("creditBalance").asInt())
                .myCash(node.get("myCash").asInt())
                .build();

        AccountSettings accountSettings = AccountSettings.builder()
                .enablefollowme(node.get("enablefollowme").asBoolean())
                .sendmenotifications(node.get("sendmenotifications").asBoolean())
                .sendTextmessages(node.get("sendTextmessages").asBoolean())
                .enabletagging(node.get("enabletagging").asBoolean())
                .build();

        ProfileSettings profileSettings = ProfileSettings.builder()
                .userType(node.get("userType").asInt())
                .userStatus(node.get("userStatus").asInt())
                .profilePicture(node.get("profilePicture").asText())
                .coverPicture(node.get("coverPicture").asText())
                .createdAt(LocalDateTime.parse(node.get("createdAt").asText()))
                .updatedAt(LocalDateTime.parse(node.get("updatedAt").asText()))
                .build();


        return Employee.builder()
                .privateInfo(privateInfo)
                .locationInfo(locationInfo)
                .paymentInfo(paymentInfo)
                .profileSettings(profileSettings)
                .accountSettings(accountSettings)
                .build();
    }
}