package com.example.todo.service;

import com.example.todo.domain.dto.TodoDto;

public interface TodoService {
    public TodoDto create(TodoDto request);
    public TodoDto setStatusTrue(Long id);
    public TodoDto setStatusFalse(Long id);
}
