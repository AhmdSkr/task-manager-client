package com.rakkiz.taskmanagerclient.view.strategy.type;

import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.strategy.TaskFilter;

public class TypeTaskFilter implements TaskFilter {

    @Override
    public boolean filter(Task task){return true;}
}
