package com.example.todo.service;

import com.example.todo.domain.dto.TodoDto;

public interface TodoService {
    public TodoDto create(TodoDto request);
}
