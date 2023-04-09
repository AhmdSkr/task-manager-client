package com.rakkiz.taskmanagerclient;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DatabaseConnectionTest extends TestCase {

    public static final String DBMS_NAME = "derby";
    public static final String DATABASE_NAME = "TaskManager";
    public static final String JDBC_URL = String.format("jdbc:%s:%s;create=true", DBMS_NAME, DATABASE_NAME);
    public Connection connection;

    @Test
    public void testConnectToDatabase() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
        } catch(SQLException exception) {
            fail("An SQLException should not be thrown at connection.");
        }
        assertNotNull("The connection object created should not be null.", connection);
    }

    @AfterAll
    public void testCloseConnection() {
        try {
            connection.close();
        } catch(SQLException exception) {
            fail("An SQLException should not be thrown when closing connection.");
        }
    }
}
