package com.example.todolist.controller;

import com.example.todolist.dto.TaskRequest;
import com.example.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("newTask", new TaskRequest("")); // Usando TaskRequest
        return "tasks";
    }

    @PostMapping
    public String addTask(@Valid @ModelAttribute("newTask") TaskRequest request) {
        taskService.createTask(request); // Chamando createTask com TaskRequest
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTaskCompletion(@PathVariable Long id) {
        taskService.toggleTaskCompletion(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}