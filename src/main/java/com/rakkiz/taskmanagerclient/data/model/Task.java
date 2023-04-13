package com.rakkiz.taskmanagerclient.data.model;
import java.time.Instant;
import java.util.Objects;

public class Task {

    public static final String DEFAULT_NAME = "Untitled";
    public static final String DEFAULT_DESCRIPTION = "";

    private Integer taskId;
    private String name;
    private String description;
    private final Instant createdAt;

    /**
     * Creates a task with:
     * <ul>
     *     <li>no ID</li>
     *     <li>name as <b>"Untitled"</b></li>
     *     <li><b>empty</b> description</li>
     *     <li>creation instant as <b>now</b></li>
     * </ul>
     */
    public Task() {
        this.taskId = null;
        this.name = DEFAULT_NAME;
        this.description = DEFAULT_DESCRIPTION;
        this.createdAt = Instant.now();
    }

    /**
     * Creates a task with given attributes
     * @param taskId Task's ID
     * @param name Task's Name
     * @param description Task's Description
     * @param createdAt Task's Creation Instant
     */
    public Task(Integer taskId, String name, String description, Instant createdAt) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
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
     * @return task's creation time
     */
    public Instant getCreationTime() {
        return this.createdAt;
    }

    /**
     * sets the ID of given task
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
        this.name = name;
    }
    /**
     * sets the description of a specific task
     * @param description task's new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Two tasks are equal if they own the identical IDs.<br/>
     * <b>Two null IDs</b> are <b>not</b> equal.
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

