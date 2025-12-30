package com.poductivity_mangement.productivity.service;

import com.poductivity_mangement.productivity.DTO.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTaskService {

    private final List<Task> userTasks = new ArrayList<>();

    public void addTask(Task task) {
        userTasks.add(task);
    }

    public List<Task> getTasks() {
        return userTasks;
    }

    public void deleteTask(String taskId) {
        userTasks.removeIf(t -> t.getId().equals(taskId));
    }
}
