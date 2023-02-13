package controller;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.Employee;

import java.io.File;
import java.io.IOException;

public class XmlWriter {

    private static XmlWriter instance;

    private XmlWriter() {
    }

    public static XmlWriter getInstance() {
        if (instance == null)
            instance = new XmlWriter();
        return instance;
    }

    public void writeEmployeeList(Employee[] employeeList, String xmlPath) throws IOException {
        XmlMapper mapper = new XmlMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(Employee.class, new EmployeeSerializer());
        mapper.registerModule(module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        mapper.writeValue(
                new File(xmlPath),
                employeeList
        );
    }
}
