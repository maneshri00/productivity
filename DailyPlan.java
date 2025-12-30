package com.poductivity_mangement.productivity.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyPlan {

    private LocalDate date;
    private List<TimeSlot> slots = new ArrayList<>();

    public DailyPlan(LocalDate date) {
        this.date = date;
    }

    public void addSlot(TimeSlot slot) {
        slots.add(slot);
    }

    public List<TimeSlot> getSlots() {
        return slots;
    }

    public LocalDate getDate() {
        return date;
    }
}
