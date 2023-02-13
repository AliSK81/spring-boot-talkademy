package controller;

import model.Employee;

import java.io.IOException;

public class FileConverter {

    private static FileConverter instance;

    private FileConverter() {
    }

    public static FileConverter getInstance() {
        if (instance == null)
            instance = new FileConverter();
        return instance;
    }

    public void Json2Xml(String jsonPath, String xmlPath) throws IOException {
        JsonReader jsonReader = JsonReader.getInstance();
        XmlWriter xmlManager = XmlWriter.getInstance();
        Employee[] employeeList;

        employeeList = jsonReader.readEmployeeList(jsonPath);
        xmlManager.writeEmployeeList(employeeList, xmlPath);  
    }
}
