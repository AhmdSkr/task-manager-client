package com.rakkiz.management.task.client.view.factory.Filter;

import com.rakkiz.management.task.client.controller.TaskSackController;
import javafx.scene.Node;

import java.io.IOException;

public interface FilterVeiwFactory {

    /**
     * Creates a new Filter view for the Task Sack.
     *
     * @param type               String to identify the type of filter
     * @param taskSackController Controller to assign the filters to the task sack
     * @return Filter view's root element
     */
    Node create(String type, TaskSackController taskSackController) throws IOException;
}
