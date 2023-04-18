package com.rakkiz.taskmanagerclient.data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDatabaseConnector {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby:TaskManager;create=true");
    }
}
