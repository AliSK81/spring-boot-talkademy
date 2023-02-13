package controller;

import model.Course;
import model.Professor;
import model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MySQLManagerTest {

    private MySQLManager dbMgr;

    @BeforeEach
    void setUp() {
        dbMgr = MySQLManager.getInstance();
        dbMgr.startConnection(
                "jdbc:mysql://localhost/CourseManagementTest?allowMultiQueries=true",
                "alisk",
                "aliskalisk"
        );
    }

    @AfterEach
    void tearDown() {
        try {
            resetDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbMgr.closeConnection();
    }

    @Test
    void startConnectionTest() {
        assertNotNull(dbMgr.getConnection());
    }

    @Test
    void closeConnectionTest() {
        dbMgr.closeConnection();
        try {
            assertTrue(dbMgr.getConnection().isClosed());
        } catch (SQLException e) {
            fail(e);
        }
    }

    @Test
    void insertStudentTest() {
        int rowsEffected = dbMgr.insertStudent(buildStudent());
        assertEquals(1, rowsEffected);
    }

    @Test
    void insertCourseTest() {
        int rowsEffected = dbMgr.insertCourse(buildCourse());
        assertEquals(1, rowsEffected);
    }

    @Test
    void insertProfessorTest() {
        int rowsEffected = dbMgr.insertProfessor(buildProfessor());
        assertEquals(1, rowsEffected);
    }

    @Test
    void insertSectionTest() {
        dbMgr.insertStudent(buildStudent());
        dbMgr.insertCourse(buildCourse());
        dbMgr.insertProfessor(buildProfessor());
        dbMgr.insertStudentCourse(1, 1);
        int rowsEffected = dbMgr.insertSection(1, 1);
        assertEquals(1, rowsEffected);
    }

    @Test
    void insertStudentCourseTest() {
        dbMgr.insertStudent(buildStudent());
        dbMgr.insertCourse(buildCourse());
        int rowsEffected = dbMgr.insertStudentCourse(1, 1);
        assertEquals(1, rowsEffected);
    }

    @Test
    void deleteStudentCourseTest() {
        dbMgr.insertStudent(buildStudent());
        dbMgr.insertCourse(buildCourse());
        dbMgr.insertStudentCourse(1, 1);
        int rowsEffected = dbMgr.deleteStudentCourse(1, 1);
        assertEquals(1, rowsEffected);
    }

    @Test
    void updateStudentFavCourseTest() {
        dbMgr.insertCourse(buildCourse());
        dbMgr.insertStudent(buildStudent());
        int rowsEffected = dbMgr.updateStudentFavCourse(1, 1);
        assertEquals(1, rowsEffected);
    }

    @Test
    void updateStudentCourseScoreTest() {
        dbMgr.insertCourse(buildCourse());
        dbMgr.insertStudent(buildStudent());
        dbMgr.insertProfessor(buildProfessor());
        dbMgr.insertSection(1, 1);
        dbMgr.insertStudentCourse(1, 1);
        int rowsEffected = dbMgr.updateStudentCourseScore(1, 1, 10);
        assertEquals(1, rowsEffected);
    }

    @Test
    void selectCourseAverageScoreTest() {
        dbMgr.insertCourse(buildCourse());
        dbMgr.insertStudent(buildStudent());
        dbMgr.insertProfessor(buildProfessor());
        dbMgr.insertSection(1, 1);
        dbMgr.insertStudentCourse(1, 1);
        dbMgr.updateStudentCourseScore(1, 1, 11);
        double actualAvg = dbMgr.selectCourseAverageScore(1);
        assertEquals(11, actualAvg);
    }

    @Test
    void selectStudentsByGPATest() {
        dbMgr.insertCourse(buildCourse());
        dbMgr.insertStudent(buildStudent());
        dbMgr.insertProfessor(buildProfessor());
        dbMgr.insertSection(1, 1);
        dbMgr.insertStudentCourse(1, 1);
        dbMgr.updateStudentCourseScore(1, 1, 15);
        dbMgr.updateGPA(1);
        ArrayList<Student> students = dbMgr.selectStudentsByGPA(10);
        assertEquals(1, students.size());
        assertEquals(15, students.get(0).getGPA());
    }

    @Test
    void selectStudentCountForFavCoursesTest() {
        dbMgr.insertCourse(buildCourse());
        dbMgr.insertStudent(buildStudent());
        dbMgr.updateStudentFavCourse(1, 1);
        HashMap<Integer, Integer> favCourses = dbMgr.selectStudentCountForFavCourses();
        assertEquals(1, favCourses.size());
        assertEquals(1, favCourses.get(1));
    }

    @Test
    void selectCourseByIdTest() {
        dbMgr.insertCourse(buildCourse());
        Course course = dbMgr.selectCourseById(1);
        assertEquals(1, course.getId());
        assertEquals("TestName", course.getName());
    }

    @Test
    void updateGPATest() {
        dbMgr.insertCourse(buildCourse());
        dbMgr.insertStudent(buildStudent());
        dbMgr.insertProfessor(buildProfessor());
        dbMgr.insertSection(1, 1);
        dbMgr.insertStudentCourse(1, 1);
        dbMgr.updateStudentCourseScore(1, 1, 20);
        dbMgr.updateGPA(1);
        int effectedRows = dbMgr.updateGPA(1);
        assertEquals(1, effectedRows);
    }

    private Student buildStudent() {
        return Student.builder().fName("TestFirstName").lName("TestLastName").build();
    }

    private Professor buildProfessor() {
        return Professor.builder().fName("TestFirstName").lName("TestLastName").build();
    }

    private Course buildCourse() {
        return Course.builder().name("TestName").capacity(10).build();
    }

    private void resetDatabase() throws SQLException {
        if (dbMgr.getConnection().isClosed()) return;
        String query = "DROP TABLE IF EXISTS Student_Course;" +
                "DROP TABLE IF EXISTS Section;" +
                "DROP TABLE IF EXISTS Student;" +
                "DROP TABLE IF EXISTS Professor;" +
                "DROP TABLE IF EXISTS Course;";
        try (Statement statement = dbMgr.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}