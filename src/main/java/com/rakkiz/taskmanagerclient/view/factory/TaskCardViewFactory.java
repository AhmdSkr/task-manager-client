package com.rakkiz.taskmanagerclient.view.factory;


import com.rakkiz.taskmanagerclient.data.model.Task;
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
    Node create(Task task) throws Exception;
}
