package com.rakkiz.management.task.client.data;

import com.rakkiz.management.task.client.data.model.Task;

import java.time.Instant;
import java.util.List;

public interface TaskRepository extends AutoCloseable {

    /**
     * creates a record of Task model in the database
     *
     * @param task Task model to be recorded
     * @throws Exception if task is null (or non-null attributes are null) or right for creation on database not granted
     */
    void create(Task task) throws Exception;

    /**
     * retrieves all Task models from database
     * @return list of Tasks
     * @throws Exception if right for reading on database not granted
     */
    List<Task> getAllTasks() throws Exception;

    /**
     * retrieves Task model with given ID from database
     * @param taskId task's ID
     * @return null if not found, else returns retrieved Task
     * @throws Exception if right for reading on database not granted
     */
    Task getByTaskId(Integer taskId) throws Exception;

    /**
     * retrieves a list of Task models from database, where models' creation date belonging to supplied interval
     * @param start start of interval
     * @param durationAfter length of interval
     * @return list of Tasks
     * @throws Exception if right for reading on database not granted
     */
    List<Task> getByCreationInterval(Instant start, Integer durationAfter) throws Exception;
    /**
     * retrieves a list of Task models from database, where models' Scheduled date belonging to supplied interval
     * @param start start of interval
     * @param durationAfter length of interval
     * @return list of Tasks
     * @throws Exception if right for reading on database not granted
     */
    List<Task> getByScheduledForInterval(Instant start, Integer durationAfter) throws Exception;

    /**
     * updates Task model record in database
     * @param task model to be updated (task's ID != null)
     * @throws Exception if right for update on database not granted
     */
    void update(Task task) throws Exception;

    /**
     * delete Task model's record from database
     *
     * @param task target Task model
     * @throws Exception if right for delete on database not granted
     */
    void delete(Task task) throws Exception;
}
