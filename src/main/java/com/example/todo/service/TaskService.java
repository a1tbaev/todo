package com.example.todo.service;

import com.example.todo.dto.SimpleResponse;
import com.example.todo.dto.TaskRequest;
import com.example.todo.entity.Task;

import java.util.List;

public interface TaskService {
    SimpleResponse save(TaskRequest taskRequest);
    SimpleResponse delete(Long id);
    SimpleResponse update(Long id, TaskRequest taskRequest);
    Task getById(Long id);
    List<Task> getAll();
}
