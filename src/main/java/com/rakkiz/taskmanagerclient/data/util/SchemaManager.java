package com.rakkiz.taskmanagerclient.data.util;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemaManager {

    public static String TASK_TABLE = "TASK";
    public static String TASK_ID = "TASK_ID";
    public static String TASK_NAME = "TASK_NAME";
    public static String TASK_DESC = "TASK_DESC";
    public static String TASK_CREATED_AT = "CREATED_AT";

    private final Connection connection;

    public SchemaManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves the schemas sql data definition scripts for schema creation
     * @return schema's creation script
     */
    public String getCreationDefinition() {

        String tableDefinitionFormat = """
        CREATE TABLE %s (
            %s  INT             NOT NULL    GENERATED ALWAYS AS IDENTITY    PRIMARY KEY,
            %s  VARCHAR(%d)     NOT NULL,
            %s  VARCHAR(%d),
            %s  TIMESTAMP,
            %s  INT             NOT NULL    DEFAULT %d,
            %s  TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP,
            %s  TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP   ON UPDATE CURRENT_TIMESTAMP
        )
        
        """;

        return String.format(
                tableDefinitionFormat,
                TASK_TABLE,
                TASK_ID,
                TASK_NAME,
                Task.NAME_LEN_MAX,
                TASK_DESC,
                Task.DESC_LEN_MAX,
                TASK_SCHEDULED_FOR,
                TASK_DURATION,
                Task.DEFAULT_DURATION,
                TASK_CREATED_AT,
                TASK_UPDATED_AT
        );
    }

    /**
     * Retrieves the schemas sql data definition scripts for schema destruction
     * @return schema's destruction script
     */
    public String getDestructionDefinition() {
        String tableDefinitionFormat = """
        DROP TABLE %s
        """;
        return String.format(tableDefinitionFormat, TASK_TABLE);
    }

    /**
     * Checks if schema already created on database
     * @return true if schema is created, otherwise false
     * @throws SQLException if access to database's metadata not granted
     */
    protected boolean isEstablished() throws SQLException {
        DatabaseMetaData meta =  connection.getMetaData();
        ResultSet result = meta.getTables(null, null, TASK_TABLE, new String[] {"TABLE"});
        while(result.next()) {
            if(result.getString("TABLE_NAME").equalsIgnoreCase(TASK_TABLE))
                return true;
        }
        return false;
    }

    /**
     * Creates schema on database if not created
     * @throws SQLException if right to modify schema on database not granted
     */
    public void establishSchema() throws SQLException {
        if(isEstablished()) return;
        connection.createStatement().executeUpdate(getCreationDefinition());
    }

    /**
     * Destroys corresponding schema on database
     * @throws SQLException if right to modify schema on database not granted
     */
    public void drop() throws SQLException {
        connection.createStatement().executeUpdate(getDestructionDefinition());
    }
}
