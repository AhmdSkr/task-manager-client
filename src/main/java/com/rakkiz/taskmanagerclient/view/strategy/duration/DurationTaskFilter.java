package com.rakkiz.taskmanagerclient.view.strategy.duration;

import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.strategy.TaskFilter;

public abstract class DurationTaskFilter implements TaskFilter {

    public abstract boolean filter(Task task);
}
