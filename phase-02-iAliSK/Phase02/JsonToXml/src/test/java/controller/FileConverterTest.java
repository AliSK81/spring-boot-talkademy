package controller;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileConverterTest {

    private String srcPath;
    private String dstPath;

    @Test
    void testJson2XmlFileNotFound() {
        srcPath = "path/to/invalid/file.json";
        dstPath = "src/test/resources/EmployeeDataTest.xml";

        Exception exception = assertThrows(FileNotFoundException.class,
                () -> FileConverter.getInstance().Json2Xml(srcPath, dstPath));

        String expectedMessage = "No such file";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testJson2Xml() throws IOException {
        srcPath = "src/test/resources/EmployeeData.json";
        dstPath = "src/test/resources/EmployeeDataTest.xml";

        FileConverter.getInstance().Json2Xml(srcPath, dstPath);

        File expectedFile = new File("src/test/resources/EmployeeData.xml");
        File actualFile = new File(dstPath);

        assertTrue(FileUtils.contentEquals(expectedFile, actualFile));
    }
}