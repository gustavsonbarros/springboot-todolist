package com.example.todolist.dto;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String description,
        boolean completed,
        LocalDateTime createdAt
) {}