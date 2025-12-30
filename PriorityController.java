package com.poductivity_mangement.productivity.controller;

import com.poductivity_mangement.productivity.DTO.Task;
import com.poductivity_mangement.productivity.DTO.UserGoalProfile;
import com.poductivity_mangement.productivity.service.TaskAggregationService;
//import com.poductivity_mangement.productivity.service.UserGoalService;
import com.poductivity_mangement.productivity.service.UserGoalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/priority")
public class PriorityController {

    private final TaskAggregationService aggregationService;
    private final UserGoalService userGoalService;

    public PriorityController(
            TaskAggregationService aggregationService,
            UserGoalService userGoalService
    ) {
        this.aggregationService = aggregationService;
        this.userGoalService = userGoalService;
    }

    @GetMapping("/tasks")
    public List<Task> getPrioritizedTasks() throws IOException {

        UserGoalProfile user = userGoalService.getCurrentProfile();

        if (user == null) {
            throw new IllegalStateException("User goals not set");
        }

        return aggregationService.getPrioritizedTasks(user);
    }
}
