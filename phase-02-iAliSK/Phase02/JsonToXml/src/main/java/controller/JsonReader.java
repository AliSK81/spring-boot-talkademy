package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import model.Employee;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    private static JsonReader instance;

    private JsonReader() {
    }

    public static JsonReader getInstance() {
        if (instance == null)
            instance = new JsonReader();
        return instance;
    }

    public Employee[] readEmployeeList(String jsonPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Employee.class, new EmployeeDeserializer());
        mapper.registerModule(module);

        return mapper.readValue(
                new File(jsonPath),
                Employee[].class
        );
    }
}
