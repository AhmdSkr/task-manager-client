package com.rakkiz.taskmanagerclient.data.util;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemaManager {

    public static String TASK_TABLE = "task";
    public static String TASK_ID = "task_id";
    public static String TASK_NAME = "task_name";
    public static String TASK_DESC = "task_desc";
    public static String TASK_CREATED_AT = "task_desc";

    private final Connection connection;

    public SchemaManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves the schemas sql data definition scripts for creation
     * @return schema's creation script
     */
    public String getCreationDefinition() {

        String tableDefinitionFormat = """
        CREATE TABLE %s (
            %s  INT             NOT NULL    PRIMARY KEY,
            %s  VARCHAR(%d)     NOT NULL,
            %s  VARCHAR(%d),
            %s  TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP
        );
        
        """;

        return String.format(
                tableDefinitionFormat,
                TASK_TABLE,
                TASK_ID,
                TASK_NAME,
                Task.NAME_LEN_MAX,
                TASK_DESC,
                Task.DESC_LEN_MAX,
                TASK_CREATED_AT
        );
    }
    public String getDestructionDefinition() {
        String tableDefinitionFormat = """
        DROP TABLE %s
        """;
        return String.format(tableDefinitionFormat, TASK_TABLE);
    }

    protected boolean isEstablished() throws SQLException {
        DatabaseMetaData meta =  connection.getMetaData();
        ResultSet result = meta.getTables(null, null, TASK_TABLE, new String[] {"TABLE"});
        while(result.next()) {
            if(result.getString("TABLE_NAME").equalsIgnoreCase(TASK_NAME))
                return true;
        }
        return false;
    }

    public void establishSchema() throws SQLException {
        if(isEstablished()) return;
        connection.createStatement().executeUpdate(getCreationDefinition());
    }

    public void drop() throws SQLException {
        connection.createStatement().executeUpdate(getDestructionDefinition());
    }
}
