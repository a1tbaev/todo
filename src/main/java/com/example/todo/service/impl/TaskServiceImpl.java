package com.example.todo.service.impl;

import com.example.todo.dto.SimpleResponse;
import com.example.todo.dto.TaskRequest;
import com.example.todo.entity.Task;
import com.example.todo.exception.NotFoundException;
import com.example.todo.repository.TaskRepository;
import com.example.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public SimpleResponse save(TaskRequest taskRequest) {
        taskRepository.save(taskRequest.toEntity());
        return new SimpleResponse("Task saved", HttpStatus.OK);
    }

    @Override
    public SimpleResponse delete(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.deleteById(id);
            return new SimpleResponse("Task deleted by id: " + id, HttpStatus.OK);
        } else {
            throw new NotFoundException("Task not found by id: " + id + " for delete");
        }
    }

    @Override
    public SimpleResponse update(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Task not found by id: " + id + " for update"));
        if (taskRequest.description() != null) task.setDescription(taskRequest.description());
        task.setCompleted(taskRequest.isCompleted());

        taskRepository.save(task);
        return new SimpleResponse("Task updated by id: " + id, HttpStatus.OK);
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Task not found by id: " + id + " for get"));
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}
