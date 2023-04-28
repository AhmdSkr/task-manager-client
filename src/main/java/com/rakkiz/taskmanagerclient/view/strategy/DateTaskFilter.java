package com.rakkiz.taskmanagerclient.view.strategy;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.util.Date;

public class DateTaskFilter implements TaskFilter {

    private Date filterDate;

    public void setFilterDate(Date date) {
        this.filterDate = date;
    }

    @Override
    public boolean filter(Task task) {
        Date date = Date.from(task.getScheduledTime());
        return date.equals(this.filterDate);

    }
}
