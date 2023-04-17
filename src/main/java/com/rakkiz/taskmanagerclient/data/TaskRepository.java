package com.rakkiz.taskmanagerclient.data;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.time.Instant;
import java.util.List;

public interface TaskRepository extends AutoCloseable {

    /**
     * creates a record of Task model in the database
     * @param task Task model to be recorded
     */
    void create(Task task) throws Exception;

    /**
     * retrieves all Task models from database
     * @return list of Tasks
     */
    List<Task> getAllTasks() throws Exception;

    /**
     * retrieves Task model with given ID from database
     * @param taskId task's ID
     * @return null if not found, else returns retrieved Task
     */
    Task getByTaskId(Integer taskId) throws Exception;

    /**
     * retrieves a list of Task models from database, where models' creation date belonging to supplied interval
     * @param start start of interval
     * @param durationAfter length of interval
     * @return list of Tasks
     */
    List<Task> getByCreationInterval(Instant start, Integer durationAfter) throws Exception;

    /**
     * updates Task model record in database
     * @param task model to be updated (task's ID != null)
     */
    void update(Task task) throws Exception;

    /**
     * delete Task model's record from database
     * @param taskId Task model's ID
     */
    void delete(Integer taskId) throws Exception;
}
