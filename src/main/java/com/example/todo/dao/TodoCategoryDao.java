package com.example.todo.dao;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.todo.domain.entity.TodoCategoryEntity;

public interface TodoCategoryDao {
    public TodoCategoryEntity createTodoCategory(TodoCategoryEntity todoCategoryEntity);
}
