package com.rakkiz.taskmanagerclient.view.strategy;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.util.ArrayList;
import java.util.List;

public class DurationTaskFilter implements TaskFilter {

    private int duration;

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public List<Task> filter(List<Task> tasks) {
        List<Task> filtered = new ArrayList<Task>();
        for(Task task:tasks){
            if(task.getDuration().equals(this.duration))
                filtered.add(task);
        }
        return filtered;
    }
}
