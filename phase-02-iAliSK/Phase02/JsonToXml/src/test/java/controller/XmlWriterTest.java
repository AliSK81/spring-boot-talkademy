package controller;

import model.Employee;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlWriterTest {

    @Test
    void testWriteEmployeeList() throws IOException {
        String jsonPath = "src/test/resources/EmployeeData.json";
        String xmlPath = "src/test/resources/EmployeeDataTest.xml";

        Employee[] employeeList = JsonReader.getInstance().readEmployeeList(jsonPath);
        XmlWriter.getInstance().writeEmployeeList(employeeList, xmlPath);

        File expectedFile = new File("src/test/resources/EmployeeData.xml");
        File actualFile = new File(xmlPath);

        assertTrue(FileUtils.contentEquals(expectedFile, actualFile));
    }
}