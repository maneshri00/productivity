package com.poductivity_mangement.productivity.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/priority")
public class PriorityOverrideController {

    private final Map<String, Integer> overrides = new HashMap<>();

    @PostMapping("/{taskId}")
    public void overridePriority(
            @PathVariable String taskId,
            @RequestParam int boost
    ) {
        overrides.put(taskId, boost); // e.g. +50 or -30
    }

    public Integer getOverride(String taskId) {
        return overrides.get(taskId);
    }
}
