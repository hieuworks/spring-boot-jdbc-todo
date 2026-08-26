package com.example.todo.dao;

import com.example.todo.domain.entity.TodoCategoryEntity;
import com.example.todo.domain.entity.TodoEntity;

public interface TodoDao {
    public TodoEntity createTodo(TodoEntity request);
}
