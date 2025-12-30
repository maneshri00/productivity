package com.poductivity_mangement.productivity.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserGoalProfile {

    private String longTermGoal;       // e.g. "Become backend engineer"
    private List<String> focusAreas;   // e.g. ["DSA", "Spring", "System Design"]

    private int urgencySensitivity;    // 1–5
    private int careerWeight;          // 1–5
    private int healthWeight;          // future extension
}

