package com.poductivity_mangement.productivity.controller;

import com.poductivity_mangement.productivity.DTO.UserGoalProfile;
import com.poductivity_mangement.productivity.service.UserGoalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/goals")
public class UserGoalController {

    private final UserGoalService userGoalService;

    public UserGoalController(UserGoalService userGoalService) {
        this.userGoalService = userGoalService;
    }

    @PostMapping
    public void saveGoals(@RequestBody UserGoalProfile profile) {
        userGoalService.save(profile);
    }

    @GetMapping
    public UserGoalProfile getGoals() {
        return userGoalService.getCurrentProfile();
    }
}


