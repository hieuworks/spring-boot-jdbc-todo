package com.example.todo.service;

import com.example.todo.domain.dto.TodoCategoryDto;
import com.example.todo.domain.entity.TodoCategoryEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface TodoCategoryService {
    public TodoCategoryDto create(TodoCategoryDto request);
}
