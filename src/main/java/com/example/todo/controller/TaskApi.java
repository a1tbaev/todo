package com.example.todo.controller;

import com.example.todo.dto.SimpleResponse;
import com.example.todo.dto.TaskRequest;
import com.example.todo.entity.Task;
import com.example.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskApi {

    private final TaskService taskService;

    @Operation(summary = "This method is used to create task")
    @PostMapping
    public SimpleResponse save(@RequestBody TaskRequest taskRequest) {
        return taskService.save(taskRequest);
    }

    @Operation(summary = "This method is used to delete task by id")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return taskService.delete(id);
    }

    @Operation(summary = "This method is used to update task by id")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        return taskService.update(id, taskRequest);
    }

    @Operation(summary = "This method is used to get task by id")
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @Operation(summary = "This method is used to get all tasks")
    @GetMapping
    public List<Task> getAll() {
        return taskService.getAll();
    }
}


