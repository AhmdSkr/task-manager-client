package com.rakkiz.taskmanagerclient.data.model;
import java.time.Instant;
import java.util.Objects;

public class Task {

    public static final int NAME_LEN_MAX = 70;
    public static final int DESC_LEN_MAX = 3000;
    public static final int DURATION_MAX = 48;
    public static final Integer DEFAULT_DURATION = 1;
    private static final String ABOVE_MAX_NAME_EXCEPTION_MESSAGE = String.format(
            "Given name is greater than NAME's MAX LENGTH = (%d)",
            Task.NAME_LEN_MAX
    );
    private static final String ABOVE_MAX_DESC_EXCEPTION_MESSAGE = String.format(
            "Given description is greater than DESCRIPTION's MAX LENGTH = (%d)",
            Task.DESC_LEN_MAX
    );

    public static final String DEFAULT_NAME = "Untitled";
    public static final String DEFAULT_DESCRIPTION = "";
    private static final String ABOVE_MAX_DURATION_EXCEPTION_MESSAGE = String.format(
            "Given duration is greater than DURATION MAX = (%d)",
            Task.DURATION_MAX
    );

    private Integer taskId;
    private String name;
    private String description;
    private Instant scheduledFor;
    private Integer duration;
    private final Instant createdAt;
    private Instant updatedAt;

    /**
     * Creates a task with:
     * <ul>
     *     <li>no ID</li>
     *     <li>name as <b>"Untitled"</b></li>
     *     <li><b>empty</b> description</li>
     *     <li><b>single pomodoro cycle</b> for duration</li>
     *     <li>creation instant as <b>now</b></li>
     *     <li>update instant as <b>now</b></li>
     * </ul><br/>
     * The task will also be unscheduled by default
     */
    public Task() {
        this.taskId = null;
        this.name = DEFAULT_NAME;
        this.description = DEFAULT_DESCRIPTION;
        this.duration = DEFAULT_DURATION;
        this.scheduledFor = null;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    /**
     * Creates a task with given attributes
     *
     * @param taskId       Task's ID
     * @param name         Task's Name
     * @param description  Task's Description
     * @param scheduledFor Task's schedule time
     * @param duration     Task's schedule duration
     * @param createdAt    Task's Creation Instant
     * @param updatedAt    Task's Update Instant
     */
    public Task(Integer taskId, String name, String description, Instant scheduledFor, Integer duration, Instant createdAt, Instant updatedAt) {
        if (duration == null) duration = DEFAULT_DURATION;

        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.scheduledFor = scheduledFor;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * @return task's current ID
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * @return task's current name
     */
    public String getName() {
        return name;
    }

    /**
     * @return task's current description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return task's scheduled time
     */
    public Instant getScheduledTime() {
        return this.scheduledFor;
    }

    /**
     * @return task's duration
     */
    public Integer getDuration() {
        return this.duration;
    }

    /**
     * sets the duration of a specific task
     *
     * @param duration task's new duration
     */
    public void setDuration(Integer duration) {
        if (duration > Task.DURATION_MAX) {
            throw new IllegalArgumentException(Task.ABOVE_MAX_DURATION_EXCEPTION_MESSAGE);
        }
        this.duration = duration;
        this.updatedAt = Instant.now();
    }

    /**
     * @return task's creation time
     */
    public Instant getCreationTime() {
        return this.createdAt;
    }

    /**
     * @return task's update time
     */
    public Instant getUpdateTime() {
        return this.updatedAt;
    }

    /**
     * sets the ID of given task
     *
     * @param taskId task's new ID
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * sets the name of a specific task
     * @param name task's new name
     */
    public void setName(String name) {
        name = name.trim();
        if (name.length() > Task.NAME_LEN_MAX) {
            throw new IllegalArgumentException(Task.ABOVE_MAX_NAME_EXCEPTION_MESSAGE);
        }
        this.name = name;
        this.updatedAt = Instant.now();
    }

    /**
     * sets the description of a specific task
     *
     * @param description task's new description
     */
    public void setDescription(String description) {
        description = description.trim();
        if (description.length() > Task.DESC_LEN_MAX) {
            throw new IllegalArgumentException(Task.ABOVE_MAX_DESC_EXCEPTION_MESSAGE);
        }
        this.description = description;
        this.updatedAt = Instant.now();
    }

    /**
     * schedules a task.
     *
     * @param scheduledFor task's schedule time
     */
    public void setScheduleTime(Instant scheduledFor) {
        this.scheduledFor = scheduledFor;
        if (this.duration == null) {
            this.duration = Task.DEFAULT_DURATION;
        }
        this.updatedAt = Instant.now();
    }

    /**
     * Two tasks are equal if they own the identical IDs.<br/>
     * <b>Two null IDs</b> are <b>not</b> equal.
     *
     * @param obj to be compared with this
     * @return true if equal, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Task t) {
            return t.getTaskId()
                    .equals(this.getTaskId());
        }
        return false;
    }
    @Override
    public int hashCode() {
        Integer thisID = getTaskId();
        if(thisID == null) return -1;
        return Objects.hash(thisID);
    }
}

