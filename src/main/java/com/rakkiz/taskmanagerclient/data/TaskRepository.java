package com.rakkiz.taskmanagerclient.data;


import com.rakkiz.taskmanagerclient.data.model.Task;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class TaskRepository{
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement readAllStatement;
    private PreparedStatement readByIdStatement;
    private PreparedStatement readByCreationIntervalStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement updateStatement;
    private String tableName;
    private static final String insertFormat = "INSERT INTO %s VALUES (?,?)";
    private static final String readFormat = "SELECT * FROM %s WHERE %s";
    private static final String deleteFormat = "DELETE FROM %s WHERE %s";
    private static final String updateFormat = "UPDATE %s SET %s WHERE %s";
    public TaskRepository(Connection connection) throws SQLException {
        this.connection = connection;
        this.tableName = "task";
        this.insertStatement = this.connection.prepareStatement(String.format(insertFormat,tableName));
        this.readAllStatement = this.connection.prepareStatement("SELECT * FROM "+tableName);
        this.readByIdStatement = this.connection.prepareStatement(String.format(readFormat,tableName,"taskId = ?"));
        this.readByCreationIntervalStatement = this.connection.prepareStatement(String.format(readFormat,tableName,"createdat BETWEEN ? AND ?"));
        this.deleteStatement = this.connection.prepareStatement(String.format(deleteFormat,tableName,"taskId = ?"));
        this.updateStatement = this.connection.prepareStatement(String.format(updateFormat,tableName,"name = ?, description = ?","taskId = ?"));
    }
    /**
     * creates a record of Task model in the database
     * @param task Task model to be recorded
     */
    void create(Task task) throws SQLException{
        this.insertStatement.setString(1,task.getName());
        this.insertStatement.setString(2,task.getDescription());
        this.insertStatement.executeUpdate();
        insertStatement.close();
    }
    /**
     * retrieves all Task models from database
     * @return list of Tasks
     */
    List<Task> getAllTasks() throws SQLException{
        List<Task> tasks = new ArrayList<>();
        ResultSet rs= this.readAllStatement.executeQuery();
        while(rs.next()){
            Timestamp createdatTimestamp = rs.getTimestamp("createdat");
            Instant instant = createdatTimestamp.toInstant();
            Task task = new Task(rs.getInt("taskId"),rs.getString("name"),rs.getString("description"),instant);
            tasks.add(task);
        }
        readAllStatement.close();
        return tasks;
    }
    /**
     * retrieves Task model with given ID from database
     * @param taskId task's ID
     * @return null if not found, else returns retrieved Task
     */
    Task getByTaskId(Integer taskId) throws SQLException{
        Task task = null;
        this.readByIdStatement.setInt(1,taskId);
        ResultSet rs = readByIdStatement.executeQuery();
        if(rs.next()){
            Timestamp createdatTimestamp = rs.getTimestamp("createdat");
            Instant instant = createdatTimestamp.toInstant();
            task = new Task(rs.getInt("taskId"),rs.getString("name"),rs.getString("description"),instant);
        }
        readByIdStatement.close();
        return task;
    }
    /**
     * retrieves a list of Task models from database, where models' creation date belonging to supplied interval
     * @param start start of interval
     * @param durationAfter length of interval
     * @return list of Tasks
     */
    List<Task> getByCreationInterval(Instant start, Integer durationAfter) throws SQLException{
        List<Task> tasks = new ArrayList<>();
        readByCreationIntervalStatement.setTimestamp(1,Timestamp.from(start));
        readByCreationIntervalStatement.setInt(2,durationAfter);//should not be integer!!!
        ResultSet rs = readByCreationIntervalStatement.executeQuery();
        while(rs.next()){
            Timestamp createdatTimestamp = rs.getTimestamp("createdat");
            Instant instant = createdatTimestamp.toInstant();
            Task task = new Task(rs.getInt("taskId"),rs.getString("name"),rs.getString("description"),instant);
            tasks.add(task);
        }
        readByCreationIntervalStatement.close();
        return tasks;
    }
    /**
     * updates Task model record in database
     * @param task model to be updated (task's ID != null)
     */
    void update(Task task) throws SQLException{
        this.updateStatement.setString(1,task.getName());
        this.updateStatement.setString(2,task.getDescription());
        this.updateStatement.setInt(3,task.getTaskId());
        int rowsAffected = updateStatement.executeUpdate();
        if(rowsAffected == 0){
            throw new SQLException("Task update failed, no rows affected");
        }
    }
    /**
     * delete Task model's record from database
     * @param taskId Task model's ID
     */
    void delete(Integer taskId) throws SQLException{
        this.deleteStatement.setInt(1,taskId);
        this.deleteStatement.executeUpdate();
        deleteStatement.close();
    }
}
