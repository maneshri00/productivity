package com.poductivity_mangement.productivity.controller;

import com.poductivity_mangement.productivity.DTO.Task;
import com.poductivity_mangement.productivity.DTO.UserGoalProfile;
import com.poductivity_mangement.productivity.service.TaskAggregationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskAggregationService taskService;

    public TaskController(TaskAggregationService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/priority")
    public List<Task> getPriorityTasks() throws IOException {

        UserGoalProfile user = new UserGoalProfile();
        user.setLongTermGoal("Backend Engineer");
        user.setFocusAreas(List.of("spring", "api", "backend", "database"));
        user.setUrgencySensitivity(2);
        user.setCareerWeight(2);

        return taskService.getPrioritizedTasks(user);
    }
}

