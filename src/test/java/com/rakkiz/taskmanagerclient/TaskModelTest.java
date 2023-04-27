package com.rakkiz.taskmanagerclient;

import com.rakkiz.taskmanagerclient.data.model.Task;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class TaskModelTest extends TestCase {

    public static Integer id = 1;
    public static String name = "thisName";
    public static String description = "thisDescription";
    public static Integer duration = 2;
    public static Instant sampleInstant = Instant.now();
    public Task task;

    @BeforeEach
    public void initTask() {
        task = new Task(id, name, description, sampleInstant, duration, sampleInstant, sampleInstant);
    }

    @Test
    public void testGetDefaultID() {
        task = new Task();
        assertNull(task.getTaskId());
    }

    @Test
    public void testGetDefaultName() {
        task = new Task();
        assertEquals(task.getName(), Task.DEFAULT_NAME);
    }

    @Test
    public void testGetDefaultDescription() {
        task = new Task();
        assertEquals(task.getDescription(), Task.DEFAULT_DESCRIPTION);
    }

    @Test
    public void testGetDefaultScheduledFor() {
        task = new Task();
        assertNull(task.getScheduledTime());
    }

    @Test
    public void testGetDefaultDuration() {
        task = new Task();
        assertEquals(Task.DEFAULT_DURATION, task.getDuration());
    }

    @Test
    public void testGetID() {
        assertEquals(id, task.getTaskId());
    }

    @Test
    public void testGetName() {
        assertEquals(name, task.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals(description, task.getDescription());
    }

    @Test
    public void testGetScheduledTime() {
        assertEquals(sampleInstant, task.getScheduledTime());
    }

    @Test
    public void testGetDuration() {
        assertEquals(duration, task.getDuration());
    }

    @Test
    public void testGetCreationTime() {
        assertEquals(sampleInstant, task.getCreationTime());
    }

    @Test
    public void testGetUpdateTime() {
        assertEquals(sampleInstant, task.getUpdateTime());
    }

    @Test
    public void testSetID() {
        Integer id = 2;
        task.setTaskId(id);
        assertEquals(task.getTaskId(), id);
    }

    @Test
    public void testSetName() {
        String name = "Hello World";
        Instant beforeUpdate = Instant.now();
        task.setName(name);
        Instant onUpdate = task.getUpdateTime();
        task.setDescription(name);

        assertEquals(task.getName(), name);
        assertFalse(onUpdate.isBefore(beforeUpdate));
        assertFalse(task.getUpdateTime().isBefore(onUpdate));
    }

    @Test
    public void testSetDescription() {
        String description = "This is a Hello World Task.";
        Instant beforeUpdate = Instant.now();
        task.setDescription(description);
        Instant onUpdate = task.getUpdateTime();
        task.setDescription(description);

        assertEquals(task.getDescription(), description);
        assertFalse(onUpdate.isBefore(beforeUpdate));
        assertFalse(task.getUpdateTime().isBefore(onUpdate));
    }

    @Test
    public void testSetScheduleTime() {
        Instant scheduleTime = Instant.now().plusMillis(1000);
        Instant beforeUpdate = Instant.now();
        task.setScheduleTime(scheduleTime);
        Instant onUpdate = task.getUpdateTime();
        task.setScheduleTime(scheduleTime);

        assertFalse(onUpdate.isBefore(beforeUpdate));
        assertFalse(task.getUpdateTime().isBefore(onUpdate));
        assertEquals(task.getScheduledTime(), scheduleTime);
    }

    @Test
    public void testSetDuration() {
        Integer duration = 2;
        Instant beforeUpdate = Instant.now();
        task.setDuration(1);
        Instant onUpdate = task.getUpdateTime();
        task.setDuration(duration);

        assertEquals(task.getDuration(), duration);
        assertFalse(onUpdate.isBefore(beforeUpdate));
        assertFalse(task.getUpdateTime().isBefore(onUpdate));
    }

}
