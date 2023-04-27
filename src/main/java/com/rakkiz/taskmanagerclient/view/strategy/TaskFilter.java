package com.rakkiz.taskmanagerclient.view.strategy;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.util.List;

public interface TaskFilter {
    List<Task> filter(List<Task> tasks);
}
