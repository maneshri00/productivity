package com.poductivity_mangement.productivity.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Task {
    private Integer manualPriorityBoost; // nullable
    private boolean fixed;
    private String id;
    private TaskSource source; // GMAIL, NOTION

    private String title;
    private String content;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;

    private TaskStatus status; // TODO, IN_PROGRESS, DONE

    private int priorityScore;
    private List<String> reasons = new ArrayList<>();

    private LocalDateTime fixedStartTime;   // null if flexible
    private LocalDateTime fixedEndTime;     // null if flexible

    private Integer estimatedMinutes;       // for flexible tasks

}
