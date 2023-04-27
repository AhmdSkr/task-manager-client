package com.rakkiz.taskmanagerclient.view.strategy;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTaskFilter implements TaskFilter {

    private Date filterDate;

    public void setFilterDate(Date date) {
        this.filterDate = date;
    }

    @Override
    public List<Task> filter(List<Task> tasks) {
        List<Task> filtered = new ArrayList<Task>();
        Date date;
        for (Task task : tasks) {
            date = Date.from(task.getScheduledTime());
            if( date.equals(this.filterDate))
                filtered.add(task);
        }
        return filtered;
    }
}
