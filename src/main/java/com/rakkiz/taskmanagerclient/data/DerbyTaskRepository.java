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
    private static DerbyTaskRepository instance = null;
    private final Connection connection;

    private final PreparedStatement insertStatement;
    private final PreparedStatement readAllStatement;
    private final PreparedStatement readByIdStatement;
    private final PreparedStatement readByCreationIntervalStatement;
    private final PreparedStatement readByScheduledIntervalStatement;
    private final PreparedStatement deleteStatement;
    private final PreparedStatement updateStatement;

    private static final String insertFormat = "INSERT INTO %s (%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?)";
    private static final String readFormat = "SELECT * FROM %s WHERE %s";
    private static final String deleteFormat = "DELETE FROM %s WHERE %s";
    private static final String updateFormat = "UPDATE %s SET %s WHERE %s";
    private static final String orderByFormat = " ORDER BY %s DESC";

    /**
     * @throws SQLException if access is not granted to database or if connection is not valid
     */
    private DerbyTaskRepository() throws SQLException {
        this.connection = new DerbyDatabaseConnector().getConnection();
        new SchemaManager(connection).establishSchema();
        String tableName = SchemaManager.TASK_TABLE;
        this.insertStatement = this.connection.prepareStatement(
                String.format(
                        insertFormat,
                        tableName,
                        SchemaManager.TASK_NAME,
                        SchemaManager.TASK_DESC,
                        SchemaManager.TASK_SCHEDULED_FOR,
                        SchemaManager.TASK_DURATION,
                        SchemaManager.TASK_CREATED_AT,
                        SchemaManager.TASK_UPDATED_AT
                ), Statement.RETURN_GENERATED_KEYS);
        this.readAllStatement = this.connection.prepareStatement("SELECT * FROM " + tableName + String.format(orderByFormat,SchemaManager.TASK_UPDATED_AT));
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
        this.readByScheduledIntervalStatement = this.connection.prepareStatement(
                String.format(
                        readFormat,
                        tableName,
                        SchemaManager.TASK_SCHEDULED_FOR + " BETWEEN ? AND ?"
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
                        SchemaManager.TASK_NAME + " = ?, "
                                + SchemaManager.TASK_DESC + " = ?, "
                                + SchemaManager.TASK_SCHEDULED_FOR + " = ?, "
                                + SchemaManager.TASK_DURATION + " = ? ",
                        SchemaManager.TASK_ID + " = ?"
                ));
    }

    public static DerbyTaskRepository getInstance() throws SQLException {
        if (instance == null) instance = new DerbyTaskRepository();
        return instance;
    }

    public void create(Task task) throws SQLException {

        this.insertStatement.setString(1, task.getName());
        this.insertStatement.setString(2, task.getDescription());
        Instant scheduledTime = task.getScheduledTime();
        if (scheduledTime == null) {
            this.insertStatement.setNull(3, Types.TIMESTAMP);
        } else {
            this.insertStatement.setTimestamp(3, Timestamp.from(task.getScheduledTime()));
        }
        this.insertStatement.setInt(4, task.getDuration());
        this.insertStatement.setTimestamp(5, Timestamp.from(task.getCreationTime()));
        this.insertStatement.setTimestamp(6, Timestamp.from(task.getUpdateTime()));
        this.insertStatement.executeUpdate();
        try (ResultSet rs = this.insertStatement.getGeneratedKeys()) {
            if (rs.next()) {
                task.setTaskId(rs.getInt(1));
            }
        }
    }

    public synchronized List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (ResultSet rs = this.readAllStatement.executeQuery()) {
            while (rs.next()) {
                Timestamp scheduledTimestamp = rs.getTimestamp(SchemaManager.TASK_SCHEDULED_FOR);
                Instant scheduledTime = null;
                if (scheduledTimestamp != null) {
                    scheduledTime = scheduledTimestamp.toInstant();
                }
                tasks.add(new Task(
                        rs.getInt(SchemaManager.TASK_ID),
                        rs.getString(SchemaManager.TASK_NAME),
                        rs.getString(SchemaManager.TASK_DESC),
                        rs.getTimestamp(SchemaManager.TASK_CREATED_AT).toInstant(),
                        rs.getInt(SchemaManager.TASK_DURATION),
                        rs.getTimestamp(SchemaManager.TASK_UPDATED_AT).toInstant(),
                        scheduledTime
                ));
            }
        }
        return tasks;
    }

    public synchronized Task getByTaskId(Integer taskId) throws SQLException {
        this.readByIdStatement.setInt(1, taskId);
        try (ResultSet rs = readByIdStatement.executeQuery()) {
            if (rs.next()) {
                Timestamp scheduledTimestamp = rs.getTimestamp(SchemaManager.TASK_SCHEDULED_FOR);
                Instant scheduledTime = null;
                if (scheduledTimestamp != null) {
                    scheduledTime = scheduledTimestamp.toInstant();
                }
                return new Task(
                        rs.getInt(SchemaManager.TASK_ID),
                        rs.getString(SchemaManager.TASK_NAME),
                        rs.getString(SchemaManager.TASK_DESC),
                        scheduledTime,
                        rs.getInt(SchemaManager.TASK_DURATION),
                        rs.getTimestamp(SchemaManager.TASK_CREATED_AT).toInstant(),
                        rs.getTimestamp(SchemaManager.TASK_UPDATED_AT).toInstant()
                );
            }
        }
        return null;
    }

    public synchronized List<Task> getByCreationInterval(Instant start, Integer durationAfter) throws SQLException {
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
                        rs.getTimestamp(SchemaManager.TASK_CREATED_AT).toInstant(),
                        rs.getInt(SchemaManager.TASK_DURATION),
                        rs.getTimestamp(SchemaManager.TASK_UPDATED_AT).toInstant(),
                        rs.getTimestamp(SchemaManager.TASK_SCHEDULED_FOR).toInstant()
                ));
            }
        }

        return tasks;
    }

    public synchronized List<Task> getByScheduledForInterval(Instant start, Integer durationAfter) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        Timestamp from = Timestamp.from(start);
        Timestamp to = Timestamp.from(start.plusSeconds(durationAfter));
        readByCreationIntervalStatement.setTimestamp(1, from);
        readByCreationIntervalStatement.setTimestamp(2, to);
        try (ResultSet rs = readByScheduledIntervalStatement.executeQuery()) {
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt(SchemaManager.TASK_ID),
                        rs.getString(SchemaManager.TASK_NAME),
                        rs.getString(SchemaManager.TASK_DESC),
                        rs.getTimestamp(SchemaManager.TASK_CREATED_AT).toInstant(),
                        rs.getInt(SchemaManager.TASK_DURATION),
                        rs.getTimestamp(SchemaManager.TASK_UPDATED_AT).toInstant(),
                        rs.getTimestamp(SchemaManager.TASK_SCHEDULED_FOR).toInstant()
                ));
            }
        }

        return tasks;
    }

    public synchronized void update(Task task) throws SQLException {
        if (task.getTaskId() == null) {
            throw new NullPointerException(NULL_ID_EXCEPTION_MESSAGE);
        }
        this.updateStatement.setString(1, task.getName());
        this.updateStatement.setString(2, task.getDescription());
        Instant scheduledTime = task.getScheduledTime();
        if (scheduledTime == null) {
            this.updateStatement.setNull(3, Types.TIMESTAMP);
        } else {
            this.updateStatement.setTimestamp(3, Timestamp.from(scheduledTime));
        }
        this.updateStatement.setInt(4, task.getDuration());
        this.updateStatement.setInt(5, task.getTaskId());
        if (updateStatement.executeUpdate() == 0) {
            throw new SQLException(UPDATE_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    public synchronized void delete(Task task) throws SQLException {
        if (task.getTaskId() == null) {
            throw new IllegalArgumentException(NULL_ID_EXCEPTION_MESSAGE);
        }
        this.deleteStatement.setInt(1, task.getTaskId());
        this.deleteStatement.executeUpdate();
        task.setTaskId(null);
    }

    @Override
    public synchronized void close() throws Exception {
        insertStatement.close();
        readAllStatement.close();
        readByIdStatement.close();
        readByCreationIntervalStatement.close();
        readByScheduledIntervalStatement.close();
        deleteStatement.close();
        updateStatement.close();
        connection.close();
        instance = null;
    }
}
