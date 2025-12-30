package com.poductivity_mangement.productivity.service;

import com.poductivity_mangement.productivity.DTO.CalendarContext;
import com.poductivity_mangement.productivity.DTO.Task;
import com.poductivity_mangement.productivity.DTO.UserGoalProfile;
import com.poductivity_mangement.productivity.service.mapper.NotionTaskMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TaskAggregationService {

    private final GmailService gmailService;
    private final NotionTaskMapper notionTaskMapper;
    private final CalendarService calendarService;
    private final PriorityEngine priorityEngine;

    public TaskAggregationService(
            GmailService gmailService,
            NotionTaskMapper notionTaskMapper,
            CalendarService calendarService,
            PriorityEngine priorityEngine
    ) {
        this.gmailService = gmailService;
        this.notionTaskMapper = notionTaskMapper;
        this.calendarService = calendarService;
        this.priorityEngine = priorityEngine;
    }

    public List<Task> getPrioritizedTasks(UserGoalProfile user) throws IOException {

        List<Task> allTasks = new ArrayList<>();

        // 1️⃣ Collect tasks
        allTasks.addAll(gmailService.readInboxAsTasks());
        allTasks.addAll(notionTaskMapper.fetchNotionTasksAsTasks());

        // 2️⃣ Calendar context
        CalendarContext context = calendarService.getTodayContext();

        // 3️⃣ Calendar → fixed tasks
        allTasks.addAll(calendarService.getFixedTasksFromCalendar());

        // 4️⃣ Score tasks
        for (Task task : allTasks) {
            priorityEngine.score(task, user, context);
        }

        // 5️⃣ Sort by priority
        allTasks.sort(
                Comparator.comparingInt(Task::getPriorityScore).reversed()
        );

        return allTasks;
    }
}
