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
    public static Instant instant = Instant.now();
    public static Integer duration = 5000;
    public Task task;

    @BeforeEach
    public void initTask() {
        task = new Task(id, name, description, instant,duration,instant,null);
    }

    @Test
    public void testGetDefaultID() {
        task = new Task();
        assertNull(task.getTaskId());
    }

    @Test
    public void testGetDefaultName() {
        task = new Task();
        assertEquals(task.getName(),Task.DEFAULT_NAME);
    }

    @Test
    public void testGetDefaultDescription() {
        task = new Task();
        assertEquals(task.getDescription(),Task.DEFAULT_DESCRIPTION);
    }

    @Test
    public void testGetID() {
        assertEquals(id,task.getTaskId());
    }

    @Test
    public void testGetName() {
        assertEquals(name,task.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals(description, task.getDescription());
    }

    @Test
    public void testGetCreationTime() {
        assertEquals(instant, task.getCreationTime());
    }
    @Test
    public void testGetScheduledTime(){assertEquals(instant,task.getScheduledTime());}
    @Test
    public void testGetDurationTime(){assertEquals(duration,task.getDuration());}
    @Test
    public void testGetUpdatedTime(){assertEquals(null,task.getUpdateTime());}

    @Test
    public void testSetID() {
        Integer id = 2;
        task.setTaskId(id);
        assertEquals(task.getTaskId(), id);
    }

    @Test
    public void testSetName() {
        String name = "Hello World";
        task.setName(name);
        assertEquals(task.getName(), name);
    }

    @Test
    public void testSetDescription() {
        String description = "This is a Hello World Task.";
        task.setDescription(description);
        assertEquals(task.getDescription(),description);
    }
    @Test
    public void testSetScheduleTime(){
        String dateString = "2023-04-27T13:45:00Z";
        Instant instant = Instant.parse(dateString);
        task.setScheduleTime(instant);
        assertEquals(task.getScheduledTime(),instant);
    }
    @Test
    public void testSetDuration(){
        Integer duration = 2;
        task.setDuration(duration);
        assertEquals(task.getDuration(),duration);
    }
}
