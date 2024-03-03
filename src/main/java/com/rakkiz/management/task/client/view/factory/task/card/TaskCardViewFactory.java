package com.rakkiz.management.task.client.view.factory.task.card;


import com.rakkiz.management.task.client.controller.TaskSackController;
import com.rakkiz.management.task.client.data.model.Task;
import javafx.scene.Node;

/**
 * The TaskCardViewFactory is responsible for loading TaskCard views into the application. <br/>
 */
public interface TaskCardViewFactory {

    /**
     * Creates a new TaskCard view for a given Task Model.
     *
     * @param task Task Model to be represented by the view
     * @return TaskCard view's root element
     * @throws Exception if task is invalid or failed to create the view.
     */
    Node create(Task task, TaskSackController taskSackController) throws Exception;
}
