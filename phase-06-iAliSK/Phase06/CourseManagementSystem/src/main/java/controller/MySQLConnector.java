package controller;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnector {
    private static MySQLConnector instance;

    @Getter
    private Connection connection;

    private String dbURL;
    private String dbUSR;
    private String dbPWD;

    private MySQLConnector() {
    }

    public static MySQLConnector getInstance() {
        if (instance == null) {
            instance = new MySQLConnector();
        }
        return instance;
    }

    public void authDatabase(String dbURL, String dbUSR, String dbPWD) {
        this.dbURL = dbURL;
        this.dbUSR = dbUSR;
        this.dbPWD = dbPWD;
    }

    public void startConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbUSR, dbPWD);

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("could not establish a connection to the MySQL database:");
            System.err.println(e.getMessage());
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Could not close database connection:");
                System.err.println(e.getMessage());
            }
        }
    }

    public void createTables() {
        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Course ("
                    + "CourseId INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                    + "Name VARCHAR(20) NOT NULL,"
                    + "Capacity INTEGER NOT NULL);");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Student ("
                    + "StudentId INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                    + "FirstName VARCHAR(20) NOT NULL,"
                    + "LastName VARCHAR(20) NOT NULL,"
                    + "FavCourseId INTEGER DEFAULT NULL,"
                    + "Qualified INTEGER DEFAULT 0,"
                    + "GPA INTEGER DEFAULT NULL,"
                    + "FOREIGN KEY (FavCourseId) REFERENCES Course(CourseId));");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Professor ("
                    + "ProfessorId INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                    + "FirstName VARCHAR(20) NOT NULL,"
                    + "LastName VARCHAR(20) NOT NULL);");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Section ("
                    + "ProfessorId INTEGER NOT NULL,"
                    + "CourseId INTEGER NOT NULL,"
                    + "PRIMARY KEY (ProfessorId, CourseId),"
                    + "FOREIGN KEY (ProfessorId) REFERENCES Professor(ProfessorId),"
                    + "FOREIGN KEY (CourseId) REFERENCES Course(CourseId));");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Student_Course ("
                    + "StudentId INTEGER NOT NULL,"
                    + "CourseId INTEGER NOT NULL,"
                    + "Score INTEGER DEFAULT NULL,"
                    + "PRIMARY KEY (StudentId, CourseId),"
                    + "FOREIGN KEY (StudentId) REFERENCES Student(StudentId),"
                    + "FOREIGN KEY (CourseId) REFERENCES Course(CourseId));");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
