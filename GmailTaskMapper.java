package com.poductivity_mangement.productivity.service.mapper;

import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.poductivity_mangement.productivity.DTO.Task;
import com.poductivity_mangement.productivity.DTO.TaskSource;
import com.poductivity_mangement.productivity.DTO.TaskStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class GmailTaskMapper {

    public static Task mapToTask(Message message) {

        Task task = new Task();
        task.setId(message.getId());
        task.setSource(TaskSource.GMAIL);
        task.setStatus(TaskStatus.TODO);

        task.setTitle("(No Subject)");
        task.setContent(message.getSnippet());
        task.setCreatedAt(parseCreatedAt(message));

        List<MessagePartHeader> headers = message.getPayload().getHeaders();

        for (MessagePartHeader header : headers) {
            if ("Subject".equalsIgnoreCase(header.getName())) {
                task.setTitle(header.getValue());
            }
        }

        return task;
    }

    private static LocalDateTime parseCreatedAt(Message message) {
        return Instant
                .ofEpochMilli(message.getInternalDate())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
