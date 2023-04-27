package com.rakkiz.taskmanagerclient.view.factory.Filter;

import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.scene.Node;

import java.io.IOException;

public interface FilterVeiwFactory {

    /**
     * Creates a new TaskCard view for a given Task Model.
     *
     * @param  type String to identify the type of filter
     * @return Filter view's root element
     */
    Node create(String type) throws IOException;
}
