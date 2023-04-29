package com.rakkiz.taskmanagerclient.view.strategy.type;

import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.strategy.TaskFilter;

public abstract class TypeTaskFilter implements TaskFilter {

    public abstract boolean filter(Task task);
}
