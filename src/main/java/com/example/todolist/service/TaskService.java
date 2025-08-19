package com.example.todolist.service;
import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.dto.TaskRequest;
import com.example.todolist.dto.TaskResponse;
import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Retorna DTO (não a entidade diretamente)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getDescription(),
                        task.isCompleted(),
                        task.getCreatedAt()))
                .toList();
    }

    // Recebe DTO (não a entidade diretamente)
    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task(request.description()); // Usando description() do record
        Task savedTask = taskRepository.save(task);
        return convertToResponse(savedTask);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    public TaskResponse toggleTaskCompletion(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setCompleted(!task.isCompleted());
        Task updatedTask = taskRepository.save(task);
        return convertToResponse(updatedTask);
    }

    // Método privado para converter Task em TaskResponse
    private TaskResponse convertToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt());
    }
}