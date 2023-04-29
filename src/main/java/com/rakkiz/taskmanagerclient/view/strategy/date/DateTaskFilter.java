package com.rakkiz.taskmanagerclient.view.strategy.date;

import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.strategy.TaskFilter;

import java.util.Date;

public abstract class DateTaskFilter implements TaskFilter {

    private Date filterDate;

    public void setDate(Date date) {
        this.filterDate = date;
    }

    @Override
    public abstract boolean filter(Task task);
}
