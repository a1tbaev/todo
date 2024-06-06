package com.example.todo.service.impl;

import com.example.todo.dto.SimpleResponse;
import com.example.todo.dto.TaskRequest;
import com.example.todo.entity.Task;
import com.example.todo.exception.NotFoundException;
import com.example.todo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        TaskRequest taskRequest = new TaskRequest("Test Task", false);
        when(taskRepository.save(any(Task.class))).thenReturn(new Task());

        SimpleResponse response = taskService.save(taskRequest);

        assertEquals("Task saved", response.message());
        assertEquals(HttpStatus.OK, response.status());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testDelete() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.delete(taskId);

        verify(taskRepository).findById(taskId);
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void testDeleteNotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> taskService.delete(taskId));

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).deleteById(taskId);
    }

    @Test
    void testUpdate() {
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest("Updated Task", true);
        Task task = new Task();
        task.setId(taskId);
        task.setDescription("Old Task");
        task.setCompleted(false);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        SimpleResponse response = taskService.update(taskId, taskRequest);

        assertEquals("Task updated by id: " + taskId, response.message());
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(taskRequest.description(), task.getDescription());
        assertTrue(task.isCompleted());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateNotFound() {
        Long taskId = 1L;
        TaskRequest taskRequest = new TaskRequest("Updated Task", true);
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> taskService.update(taskId, taskRequest));

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testGetById() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getById(taskId);

        assertEquals(taskId, foundTask.getId());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void testGetByIdNotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> taskService.getById(taskId));

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void testGetAll() {
        List<Task> tasks = List.of(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> foundTasks = taskService.getAll();

        assertEquals(2, foundTasks.size());
        verify(taskRepository, times(1)).findAll();
    }
}
