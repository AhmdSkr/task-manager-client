package com.rakkiz.taskmanagerclient.view.strategy;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.util.List;

public interface TaskFilter {
    boolean filter(Task task);
}
