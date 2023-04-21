package com.rakkiz.taskmanagerclient.data;


import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.data.util.DerbyDatabaseConnector;
import com.rakkiz.taskmanagerclient.data.util.SchemaManager;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class DerbyTaskRepository implements TaskRepository, AutoCloseable{

    public static final String UPDATE_FAILURE_EXCEPTION_MESSAGE = "Task update failed, no rows affected";
    public static final String NULL_ID_EXCEPTION_MESSAGE = "Task's ID must not be null";

    private final Connection connection;

    private final PreparedStatement insertStatement;
    private final PreparedStatement readAllStatement;
    private final PreparedStatement readByIdStatement;
    private final PreparedStatement readByCreationIntervalStatement;
    private final PreparedStatement deleteStatement;
    private final PreparedStatement updateStatement;

    private static final String insertFormat = "INSERT INTO %s (%s,%s,%s) VALUES (?,?,?)";
    private static final String readFormat = "SELECT * FROM %s WHERE %s";
    private static final String deleteFormat = "DELETE FROM %s WHERE %s";
    private static final String updateFormat = "UPDATE %s SET %s WHERE %s";

    /**
     * @throws SQLException if access is not granted to database or if connection is not valid
     */
    public DerbyTaskRepository() throws SQLException {
        this.connection = new DerbyDatabaseConnector().getConnection();
        new SchemaManager(connection).establishSchema();
        String tableName = SchemaManager.TASK_TABLE;
        this.insertStatement = this.connection.prepareStatement(
                String.format(
                        insertFormat,
                        tableName,
                        SchemaManager.TASK_NAME,
                        SchemaManager.TASK_DESC,
                        SchemaManager.TASK_CREATED_AT
                ), Statement.RETURN_GENERATED_KEYS);
        this.readAllStatement = this.connection.prepareStatement("SELECT * FROM " + tableName);
        this.readByIdStatement = this.connection.prepareStatement(
                String.format(
                        readFormat,
                        tableName,
                        SchemaManager.TASK_ID + " = ?"
                ));

        this.readByCreationIntervalStatement = this.connection.prepareStatement(
                String.format(
                        readFormat,
                        tableName,
                        SchemaManager.TASK_CREATED_AT + " BETWEEN ? AND ?"
                ));

        this.deleteStatement = this.connection.prepareStatement(
                String.format(
                        deleteFormat,
                        tableName,
                        SchemaManager.TASK_ID + " = ?"
                ));

        this.updateStatement = this.connection.prepareStatement(
                String.format(
                        updateFormat,
                        tableName,
                        SchemaManager.TASK_NAME + " = ?, " + SchemaManager.TASK_DESC + " = ?",
                        SchemaManager.TASK_ID + " = ?"
                ));
    }

    public void create(Task task) throws SQLException {
        this.insertStatement.setString(1, task.getName());
        this.insertStatement.setString(2, task.getDescription());
        this.insertStatement.setTimestamp(3, Timestamp.from(task.getCreationTime()));
        this.insertStatement.executeUpdate();
        try (ResultSet rs = this.insertStatement.getGeneratedKeys()) {
            if (rs.next()) {
                task.setTaskId(rs.getInt(1));
            }
        }
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (ResultSet rs = this.readAllStatement.executeQuery()) {
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt(SchemaManager.TASK_ID),
                        rs.getString(SchemaManager.TASK_NAME),
                        rs.getString(SchemaManager.TASK_DESC),
                        rs.getTimestamp(SchemaManager.TASK_CREATED_AT).toInstant()
                ));
            }
        }
        return tasks;
    }

    public Task getByTaskId(Integer taskId) throws SQLException {
        this.readByIdStatement.setInt(1, taskId);
        try (ResultSet rs = readByIdStatement.executeQuery()) {
            if (rs.next()) {
                return new Task(
                        rs.getInt(SchemaManager.TASK_ID),
                        rs.getString(SchemaManager.TASK_NAME),
                        rs.getString(SchemaManager.TASK_DESC),
                        rs.getTimestamp(SchemaManager.TASK_CREATED_AT).toInstant()
                );
            }
        }
        return null;
    }

    public List<Task> getByCreationInterval(Instant start, Integer durationAfter) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        Timestamp from = Timestamp.from(start);
        Timestamp to = Timestamp.from(start.plusSeconds(durationAfter));
        readByCreationIntervalStatement.setTimestamp(1, from);
        readByCreationIntervalStatement.setTimestamp(2, to);
        try (ResultSet rs = readByCreationIntervalStatement.executeQuery()) {
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt(SchemaManager.TASK_ID),
                        rs.getString(SchemaManager.TASK_NAME),
                        rs.getString(SchemaManager.TASK_DESC),
                        rs.getTimestamp(SchemaManager.TASK_CREATED_AT).toInstant()
                ));
            }
        }

        return tasks;
    }

    public void update(Task task) throws SQLException {
        if(task.getTaskId() == null) {
            throw new NullPointerException(NULL_ID_EXCEPTION_MESSAGE);
        }
        this.updateStatement.setString(1, task.getName());
        this.updateStatement.setString(2, task.getDescription());
        this.updateStatement.setInt(3, task.getTaskId());
        if (updateStatement.executeUpdate() == 0) {
            throw new SQLException(UPDATE_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    public void delete(Task task) throws SQLException {
        if (task.getTaskId() == null) {
            throw new IllegalArgumentException(NULL_ID_EXCEPTION_MESSAGE);
        }
        this.deleteStatement.setInt(1, task.getTaskId());
        this.deleteStatement.executeUpdate();
        task.setTaskId(null);
    }

    @Override
    public void close() throws Exception {
        insertStatement.close();
        readAllStatement.close();
        readByIdStatement.close();
        readByCreationIntervalStatement.close();
        deleteStatement.close();
        updateStatement.close();
        connection.close();
    }
}
