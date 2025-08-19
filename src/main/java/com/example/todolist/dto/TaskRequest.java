package com.example.todolist.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
        @NotBlank(message = "A descrição não pode estar vazia!")
        String description
) {}