package com.rakkiz.taskmanagerclient.data;


import com.rakkiz.taskmanagerclient.data.model.Task;

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

    private static final String insertFormat = "INSERT INTO %s VALUES (?,?)";
    private static final String readFormat = "SELECT * FROM %s WHERE %s";
    private static final String deleteFormat = "DELETE FROM %s WHERE %s";
    private static final String updateFormat = "UPDATE %s SET %s WHERE %s";

    public DerbyTaskRepository(Connection connection) throws SQLException {
        this.connection = connection;
        String tableName = "task";
        this.insertStatement = this.connection.prepareStatement(String.format(insertFormat,tableName));
        this.readAllStatement = this.connection.prepareStatement("SELECT * FROM "+tableName);
        this.readByIdStatement = this.connection.prepareStatement(String.format(readFormat,tableName,"taskId = ?"));
        this.readByCreationIntervalStatement = this.connection.prepareStatement(String.format(readFormat,tableName,"createdat BETWEEN ? AND ?"));
        this.deleteStatement = this.connection.prepareStatement(String.format(deleteFormat,tableName,"taskId = ?"));
        this.updateStatement = this.connection.prepareStatement(String.format(updateFormat,tableName,"name = ?, description = ?","taskId = ?"));
    }

    public void create(Task task) throws SQLException{
        this.insertStatement.setString(1,task.getName());
        this.insertStatement.setString(2,task.getDescription());
        this.insertStatement.executeUpdate();
    }

    public List<Task> getAllTasks() throws SQLException{
        List<Task> tasks = new ArrayList<>();
        ResultSet rs = this.readAllStatement.executeQuery();
        while(rs.next()) {
            tasks.add( new Task(
                    rs.getInt("taskId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getTimestamp("createdat").toInstant()
            ));
        }
        return tasks;
    }

    public Task getByTaskId(Integer taskId) throws SQLException {
        this.readByIdStatement.setInt(1,taskId);
        ResultSet rs = readByIdStatement.executeQuery();
        if(rs.next()){
            return new Task(
                    rs.getInt("taskId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getTimestamp("createdat").toInstant()
            );
        }
        return null;
    }

    public List<Task> getByCreationInterval(Instant start, Integer durationAfter) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        Timestamp from = Timestamp.from(start);
        Timestamp to = Timestamp.from(start.plusSeconds(durationAfter));
        readByCreationIntervalStatement.setTimestamp(1,from);
        readByCreationIntervalStatement.setTimestamp(2,to);
        ResultSet rs = readByCreationIntervalStatement.executeQuery();
        while(rs.next()){
            tasks.add( new Task(
                    rs.getInt("taskId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getTimestamp("createdat").toInstant()
            ));
        }
        return tasks;
    }

    public void update(Task task) throws SQLException {
        if(task.getTaskId() == null) {
            throw new NullPointerException(NULL_ID_EXCEPTION_MESSAGE);
        }
        this.updateStatement.setString(1,task.getName());
        this.updateStatement.setString(2,task.getDescription());
        this.updateStatement.setInt(3,task.getTaskId());
        if(updateStatement.executeUpdate() == 0){
            throw new SQLException(UPDATE_FAILURE_EXCEPTION_MESSAGE);
        }
    }

    public void delete(Integer taskId) throws SQLException {
        if(taskId == null) {
            throw new IllegalArgumentException(NULL_ID_EXCEPTION_MESSAGE);
        }
        this.deleteStatement.setInt(1,taskId);
        this.deleteStatement.executeUpdate();
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
