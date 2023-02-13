package controller;

import model.Course;
import model.Professor;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MySQLManager {
    private static MySQLManager instance;
    private final MySQLConnector connector;

    private MySQLManager() {
        this.connector = MySQLConnector.getInstance();
    }

    public static MySQLManager getInstance() {
        if (instance == null) {
            instance = new MySQLManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connector.getConnection();
    }

    public void startConnection(String dbURL, String dbUSR, String dbPWD) {
        connector.authDatabase(dbURL, dbUSR, dbPWD);
        connector.startConnection();
        connector.createTables();
    }

    public void closeConnection() {
        connector.closeConnection();
    }

    public int insertStudent(Student student) {
        String query = "INSERT INTO Student (FirstName, LastName) VALUES (?, ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, student.getFName());
            statement.setString(2, student.getLName());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertCourse(Course course) {
        String query = "INSERT INTO Course (Name, Capacity) VALUES (?, ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, course.getName());
            statement.setInt(2, course.getCapacity());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertProfessor(Professor professor) {
        String query = "INSERT INTO Professor (FirstName, LastName) VALUES (?, ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, professor.getFName());
            statement.setString(2, professor.getLName());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertSection(int professorId, int courseId) {
        String query = "INSERT INTO Section (ProfessorId, CourseId) VALUES (?, ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, professorId);
            statement.setInt(2, courseId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int insertStudentCourse(int studentId, int courseId) {
        String query = "INSERT INTO Student_Course (StudentId, CourseId) VALUES (?, ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteStudentCourse(int studentId, int courseId) {
        String query = "DELETE FROM Student_Course WHERE StudentId = ? AND CourseId = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateStudentFavCourse(int studentId, int courseId) {
        String query = "UPDATE Student SET FavCourseId = ? WHERE StudentId = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateStudentCourseScore(int studentId, int professorId, double score) {
        String query = "UPDATE Student_Course SC " +
                "INNER JOIN `Section` S " +
                "ON SC.CourseId = S.CourseId " +
                "SET SC.Score = ? " +
                "WHERE SC.StudentId = ? " +
                "AND S.ProfessorId = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setDouble(1, score);
            statement.setInt(2, studentId);
            statement.setInt(3, professorId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double selectCourseAverageScore(int courseId) {
        String query = "SELECT AVG(Score) AS Average FROM Student_Course " +
                "WHERE CourseId = ? " +
                "HAVING average IS NOT NULL;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, courseId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getDouble("Average");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Student> selectStudentsByGPA(double minGPA) {
        ArrayList<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student WHERE GPA >= ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setDouble(1, minGPA);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                students.add(Student.builder()
                        .id(rs.getInt("StudentId"))
                        .fName(rs.getString("FirstName"))
                        .lName(rs.getString("LastName"))
                        .favCourse(selectCourseById(rs.getInt("FavCourseId")))
                        .qualified(rs.getBoolean("Qualified"))
                        .GPA(rs.getDouble("GPA"))
                        .build()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public HashMap<Integer, Integer> selectStudentCountForFavCourses() {
        HashMap<Integer, Integer> favCourses = new HashMap<>();
        String query = "SELECT FavCourseId, Count(StudentId) FROM Student " +
                "WHERE FavCourseId IS NOT NULL " +
                "GROUP BY FavCourseId;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                favCourses.put(
                        rs.getInt("FavCourseId"),
                        rs.getInt("Count(StudentId)")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favCourses;
    }

    public Course selectCourseById(int courseId) {
        String query = "SELECT * FROM Course WHERE CourseId = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, courseId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Course.builder()
                        .id(rs.getInt("CourseId"))
                        .name(rs.getString("Name"))
                        .capacity(rs.getInt("Capacity"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateGPA(int studentId) {
        String query = "UPDATE Student S " +
                "INNER JOIN `Student_Course` SC " +
                "ON S.StudentId = SC.StudentId " +
                "SET S.GPA = (SELECT AVG(SC.Score)) " +
                "WHERE S.StudentId = ?;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, studentId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
