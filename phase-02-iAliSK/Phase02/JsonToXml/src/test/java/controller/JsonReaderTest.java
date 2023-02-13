package controller;

import model.Employee;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {


    @Test
    void testReadEmployeeListFileNotFound() {
        Exception exception = assertThrows(FileNotFoundException.class, () ->
                JsonReader.getInstance().readEmployeeList("path/to/invalid/file.json"));

        String expectedMessage = "No such file";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testReadEmployeeList() throws IOException {
        Employee[] employeeList = JsonReader.getInstance()
                .readEmployeeList("src/test/resources/EmployeeData.json");

        int exceptedValue = 4051;
        int actualValue = employeeList[0].getPrivateInfo().getId();

        assertEquals(exceptedValue, actualValue);
    }
}