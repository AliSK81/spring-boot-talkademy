package controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import model.Employee;

import java.io.IOException;

public class EmployeeSerializer extends StdSerializer<Employee> {

    public EmployeeSerializer() {
        this(null);
    }

    public EmployeeSerializer(Class<Employee> t) {
        super(t);
    }

    @Override
    public void serialize(Employee employee, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", employee.getPrivateInfo().getId());
        jgen.writeStringField("name", employee.getPrivateInfo().getName());
        jgen.writeStringField("email", employee.getPrivateInfo().getEmail());
        jgen.writeStringField("password", employee.getPrivateInfo().getPassword());
        jgen.writeStringField("about", employee.getPrivateInfo().getAbout());
        jgen.writeStringField("token", employee.getPrivateInfo().getToken());
        jgen.writeStringField("country", employee.getLocationInfo().getCountry());
        jgen.writeStringField("location", employee.getLocationInfo().getLocation());
        jgen.writeNumberField("lng", employee.getLocationInfo().getLng());
        jgen.writeNumberField("lat", employee.getLocationInfo().getLat());
        jgen.writeStringField("dob", employee.getPrivateInfo().getDob());
        jgen.writeNumberField("gender", employee.getPrivateInfo().getGender());
        jgen.writeNumberField("userType", employee.getProfileSettings().getUserType());
        jgen.writeNumberField("userStatus", employee.getProfileSettings().getUserStatus());
        jgen.writeStringField("profilePicture", employee.getProfileSettings().getProfilePicture());
        jgen.writeStringField("coverPicture", employee.getProfileSettings().getCoverPicture());
        jgen.writeBooleanField("enablefollowme", employee.getAccountSettings().isEnablefollowme());
        jgen.writeBooleanField("sendmenotifications", employee.getAccountSettings().isSendmenotifications());
        jgen.writeBooleanField("sendTextmessages", employee.getAccountSettings().isSendTextmessages());
        jgen.writeBooleanField("enabletagging", employee.getAccountSettings().isEnabletagging());
        jgen.writeStringField("createdAt", String.valueOf(employee.getProfileSettings().getCreatedAt()));
        jgen.writeStringField("updatedAt", String.valueOf(employee.getProfileSettings().getUpdatedAt()));
        jgen.writeStringField("liveLocation", employee.getLocationInfo().getLiveLocation());
        jgen.writeNumberField("livelng", employee.getLocationInfo().getLivelng());
        jgen.writeNumberField("livelat", employee.getLocationInfo().getLivelat());
        jgen.writeNumberField("creditBalance", employee.getPaymentInfo().getCreditBalance());
        jgen.writeNumberField("myCash", employee.getPaymentInfo().getMyCash());
        jgen.writeEndObject();
    }
}