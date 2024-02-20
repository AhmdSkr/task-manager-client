package com.rakkiz.management.task.client.data.util;

import com.rakkiz.management.task.client.data.model.Task;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchemaManager {

    public static String TASK_TABLE = "TASK";
    public static String TASK_UPDATE_TRIGGER = "UPDATE_AT_SETTER_TRIGGER";
    public static String TASK_ID = "TASK_ID";
    public static String TASK_NAME = "TASK_NAME";
    public static String TASK_DESC = "TASK_DESC";
    public static String TASK_SCHEDULED_FOR = "TASK_SCHEDULED_FOR";
    public static String TASK_DURATION = "TASK_DURATION";
    public static String TASK_CREATED_AT = "CREATED_AT";
    public static String TASK_UPDATED_AT = "UPDATED_AT";

    private final Connection connection;

    public SchemaManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves the schemas sql data definition scripts for schema creation
     *
     * @return schema's creation scripts (1 statement per script)
     */
    public String[] getCreationDefinitions() {

        String tableDefinitionScript = String.format(
                """
                        CREATE TABLE %1$s (
                            %2$s    INT             NOT NULL    GENERATED ALWAYS AS IDENTITY    PRIMARY KEY,
                            %3$s    VARCHAR(%4$d)   NOT NULL,
                            %5$s    VARCHAR(%6$d),
                            %7$s    TIMESTAMP,
                            %8$s    INT             NOT NULL    DEFAULT %9$d,
                            %10$s   TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP,
                            %11$s   TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP
                        )
                        """,
                TASK_TABLE,             //01
                TASK_ID,                //02
                TASK_NAME,              //03
                Task.NAME_LEN_MAX,      //04
                TASK_DESC,              //05
                Task.DESC_LEN_MAX,      //06
                TASK_SCHEDULED_FOR,     //07
                TASK_DURATION,          //08
                Task.DEFAULT_DURATION,  //09
                TASK_CREATED_AT,        //10
                TASK_UPDATED_AT         //11
        );

        String triggerDefinitionScript = String.format(
                """
                        CREATE TRIGGER %2$s AFTER UPDATE OF %5$s,%6$s,%7$s,%8$s ON %1$s
                        REFERENCING NEW AS oldRow
                        FOR EACH ROW
                        UPDATE %1$s SET %4$s = CURRENT_TIMESTAMP
                        WHERE %3$s = oldRow.%3$s
                        """,
                TASK_TABLE,             //01
                TASK_UPDATE_TRIGGER,    //02
                TASK_ID,                //03
                TASK_UPDATED_AT,        //04
                TASK_NAME,              //05
                TASK_DESC,              //06
                TASK_SCHEDULED_FOR,     //07
                TASK_DURATION           //08
        );

        return new String[]{
                tableDefinitionScript,
                triggerDefinitionScript
        };
    }

    /**
     * Retrieves the schemas sql data definition scripts for schema destruction
     *
     * @return schema's destruction scripts (1 statement per script)
     */
    public String[] getDestructionDefinitions() {
        String tableDefinitionScript = String.format(
                """
                        DROP TABLE %s
                        """,
                TASK_TABLE
        );
        String triggerDefinitionScript = String.format(
                """
                        DROP TRIGGER %s
                        """,
                TASK_UPDATE_TRIGGER
        );

        return new String[]{
                triggerDefinitionScript,
                tableDefinitionScript
        };
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
        if (isEstablished()) return;
        String[] scripts = getCreationDefinitions();
        for (String script : scripts) {
            connection.createStatement().executeUpdate(script);
        }
    }

    /**
     * Destroys corresponding schema on database
     * @throws SQLException if right to modify schema on database not granted
     */
    public void drop() throws SQLException {
        String[] scripts = getDestructionDefinitions();
        for (String script : scripts) {
            connection.createStatement().executeUpdate(script);
        }
    }
}
