package com.rakkiz.taskmanagerclient.view.strategy.date;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;

public class TodayDateTaskFilter extends DateTaskFilter{
    @Override
    public boolean filter(Task task) {
        LocalDate today = LocalDate.now();
        return task.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(today) == 0;
    }
}
