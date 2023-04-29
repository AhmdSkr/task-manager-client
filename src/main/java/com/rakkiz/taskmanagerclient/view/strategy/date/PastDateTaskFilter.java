package com.rakkiz.taskmanagerclient.view.strategy.date;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;

public class PastDateTaskFilter extends DateTaskFilter {
    @Override
    public boolean filter(Task task) {
        LocalDate yesterdayDate = LocalDate.now().minusDays(1);
        return task.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate().compareTo(yesterdayDate) < 0;
    }
}
