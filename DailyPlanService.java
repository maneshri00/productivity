package com.poductivity_mangement.productivity.service;

import com.poductivity_mangement.productivity.DTO.DailyPlan;
import com.poductivity_mangement.productivity.DTO.Task;
import com.poductivity_mangement.productivity.DTO.TimeSlot;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DailyPlanService {

    public DailyPlan generate(
            List<Task> tasks,
            LocalDateTime dayStart,
            LocalDateTime dayEnd
    ) {

        DailyPlan dailyPlan = new DailyPlan(dayStart.toLocalDate());

        // 1️⃣ Separate fixed & flexible
        List<Task> fixed = tasks.stream()
                .filter(Task::isFixed)
                .sorted(Comparator.comparing(Task::getFixedStartTime))
                .toList();

        List<Task> flexible = tasks.stream()
                .filter(t -> !t.isFixed())
                .sorted(Comparator.comparingInt(Task::getPriorityScore).reversed())
                .toList();

        LocalDateTime cursor = dayStart;
        int flexIndex = 0;

        // 2️⃣ Walk through fixed tasks
        for (Task f : fixed) {

            // Fill gap before fixed task
            while (cursor.isBefore(f.getFixedStartTime())
                    && flexIndex < flexible.size()) {

                Task t = flexible.get(flexIndex);
                int minutes = t.getEstimatedMinutes() != null
                        ? t.getEstimatedMinutes()
                        : 30;

                LocalDateTime end = cursor.plusMinutes(minutes);
                if (end.isAfter(f.getFixedStartTime())) break;

                dailyPlan.addSlot(slot(cursor, end, t));
                cursor = end;
                flexIndex++;
            }

            // Add fixed task
            dailyPlan.addSlot(
                    slot(f.getFixedStartTime(), f.getFixedEndTime(), f)
            );
            cursor = f.getFixedEndTime();
        }

        // 3️⃣ Fill remaining time
        while (cursor.isBefore(dayEnd)
                && flexIndex < flexible.size()) {

            Task t = flexible.get(flexIndex);
            int minutes = t.getEstimatedMinutes() != null
                    ? t.getEstimatedMinutes()
                    : 30;

            LocalDateTime end = cursor.plusMinutes(minutes);
            if (end.isAfter(dayEnd)) break;

            dailyPlan.addSlot(slot(cursor, end, t));
            cursor = end;
            flexIndex++;
        }

        return dailyPlan;
    }

    private TimeSlot slot(LocalDateTime s, LocalDateTime e, Task t) {
        TimeSlot ts = new TimeSlot();
        ts.setStart(s);
        ts.setEnd(e);
        ts.setTask(t);
        return ts;
    }
}
