package com.poductivity_mangement.productivity.service;


import com.poductivity_mangement.productivity.DTO.UserGoalProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGoalService {

    private UserGoalProfile currentProfile;

    public UserGoalProfile getCurrentProfile() {

        if (currentProfile == null) {
            UserGoalProfile defaultProfile = new UserGoalProfile();
            defaultProfile.setLongTermGoal("General productivity");
            defaultProfile.setFocusAreas(List.of());
            defaultProfile.setUrgencySensitivity(1);
            defaultProfile.setCareerWeight(1);
            return defaultProfile;
        }

        return currentProfile;
    }

    public void save(UserGoalProfile profile) {
        this.currentProfile = profile;
    }
}


