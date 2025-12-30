package com.poductivity_mangement.productivity.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CalendarContext {

    private int totalEventsToday;
    private boolean hasMeetingSoon;   // next 2 hours
    private List<String> eventKeywords;
}

