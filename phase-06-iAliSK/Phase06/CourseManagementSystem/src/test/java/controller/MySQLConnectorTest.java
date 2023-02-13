package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class MySQLConnectorTest {

    private MySQLConnector connector;

    @BeforeEach
    void setUp() {
        connector = MySQLConnector.getInstance();
        connector.authDatabase(
                "jdbc:mysql://localhost/CourseManagementTest",
                "alisk",
                "aliskalisk"
        );
        connector.startConnection();
    }

    @AfterEach
    void tearDown() {
        connector.closeConnection();
    }

    @Test
    void startConnectionTest() {
        assertNotNull(connector.getConnection());
    }

    @Test
    void closeConnectionTest() {
        connector.closeConnection();
        try {
            assertTrue(connector.getConnection().isClosed());
        } catch (SQLException e) {
            fail(e);
        }
    }

    @Test
    void createTablesTest() {
        connector.createTables();
        String query = "SELECT COUNT(*) AS TablesCount " +
                "FROM information_schema.tables " +
                "WHERE table_schema = ?;";
        try (PreparedStatement statement = connector.getConnection().prepareStatement(query)) {
            statement.setString(1, "CourseManagementTest");

            ResultSet rs = statement.executeQuery();
            rs.next();

            assertEquals(5, rs.getInt("TablesCount"));
        } catch (SQLException e) {
            fail(e);
        }
    }

}