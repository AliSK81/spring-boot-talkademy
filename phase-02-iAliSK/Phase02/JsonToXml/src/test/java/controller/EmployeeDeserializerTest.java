package controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EmployeeDeserializerTest {

    private EmployeeDeserializer employeeDeserializer;
    private ObjectMapper mapper;
    private JsonParser parser;

    @BeforeEach
    void setUp() throws IOException {
        String jsonPath = "src/test/resources/employee.json";
        registerDeserializer();
        parser = mapper.getFactory().createParser(new File(jsonPath));
    }

    @AfterEach
    void tearDown() throws IOException {
        parser.close();
    }

    @Test
    void testDeserialize() throws IOException {
        Employee employee = employeeDeserializer.deserialize(parser, null);
        assertEquals(employee.getPrivateInfo().getId(), 4051);
        assertFalse(employee.getAccountSettings().isEnablefollowme());
    }

    private void registerDeserializer() {
        employeeDeserializer = new EmployeeDeserializer();
        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Employee.class, employeeDeserializer);
        mapper.registerModule(module);
    }
}
