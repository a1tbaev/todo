package com.example.todo.dto;

import com.example.todo.entity.Task;
import lombok.Builder;

@Builder
public record TaskRequest(
    String description,
    boolean isCompleted
) {
    public Task toEntity() {
        Task task = new Task();
        task.setDescription(description);
        task.setCompleted(isCompleted);
        return task;
    }
}

