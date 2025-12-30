package com.poductivity_mangement.productivity.controller;

import com.poductivity_mangement.productivity.DTO.DailyPlan;
import com.poductivity_mangement.productivity.DTO.WeeklyPlan;
import com.poductivity_mangement.productivity.DTO.UserGoalProfile;
import com.poductivity_mangement.productivity.service.DailyPlanService;
import com.poductivity_mangement.productivity.service.TaskAggregationService;
import com.poductivity_mangement.productivity.service.UserGoalService;
import com.poductivity_mangement.productivity.service.WeeklyPlanService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    private final TaskAggregationService taskAggregationService;
    private final DailyPlanService dailyPlanService;
    private final WeeklyPlanService weeklyPlanService;
    private final UserGoalService userGoalService;

    public PlanController(
            TaskAggregationService taskAggregationService,
            DailyPlanService dailyPlanService,
            WeeklyPlanService weeklyPlanService,
            UserGoalService userGoalService
    ) {
        this.taskAggregationService = taskAggregationService;
        this.dailyPlanService = dailyPlanService;
        this.weeklyPlanService = weeklyPlanService;
        this.userGoalService = userGoalService;
    }

    @GetMapping("/daily")
    public DailyPlan daily(
            @RequestParam String wake,
            @RequestParam String sleep
    ) throws Exception {

        UserGoalProfile profile = userGoalService.getCurrentProfile();

        LocalDate today = LocalDate.now();

        LocalDateTime start =
                LocalDateTime.of(today, LocalTime.parse(wake));

        LocalDateTime end =
                LocalDateTime.of(today, LocalTime.parse(sleep));

        var tasks =
                taskAggregationService.getPrioritizedTasks(profile);

        return dailyPlanService.generate(tasks, start, end);
    }



    @GetMapping("/weekly")
    public WeeklyPlan weekly(
            @RequestParam(required = false) String start
    ) throws Exception {

        UserGoalProfile profile = userGoalService.getCurrentProfile();

        LocalDate weekStart =
                start == null ? LocalDate.now() : LocalDate.parse(start);

        var tasks =
                taskAggregationService.getPrioritizedTasks(profile);

        return weeklyPlanService.generate(tasks, weekStart);
    }
}
