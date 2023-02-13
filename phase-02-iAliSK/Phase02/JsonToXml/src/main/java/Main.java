import controller.FileConverter;

import java.io.IOException;

public class Main {

    private static final String jsonPath = "src/main/resources/EmployeeData.json";
    private static final String xmlPath = "src/main/resources/EmployeeData.xml";

    public static void main(String[] args) throws IOException {

        FileConverter.getInstance()
                .Json2Xml(jsonPath, xmlPath);
    }
}
