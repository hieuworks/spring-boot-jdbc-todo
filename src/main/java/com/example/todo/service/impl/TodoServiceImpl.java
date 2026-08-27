package com.example.todo.service.impl;

import com.example.todo.dao.TodoDao;
import com.example.todo.domain.dto.TodoDto;
import com.example.todo.domain.entity.TodoEntity;
import com.example.todo.mapper.Mapper;
import com.example.todo.mapper.impl.ToDoMapper;
import com.example.todo.service.TodoService;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoDao todoDao;
    private final Mapper<TodoEntity, TodoDto> mapper;

    public TodoServiceImpl(TodoDao todoDao, Mapper<TodoEntity, TodoDto> mapper) {
        this.todoDao = todoDao;
        this.mapper = mapper;
    }

    @Override
    public TodoDto create(TodoDto request) {
        TodoEntity entity= mapper.toEntity(request);
        TodoEntity savedEntity = todoDao.createTodo(entity);
        return mapper.toDto(savedEntity);
    }
    @Override
    public TodoDto setStatusTrue(Long id){
        TodoEntity toDoUpdated = todoDao.setStatusTrue(id);
        return mapper.toDto(toDoUpdated);
    }
    @Override
    public TodoDto setStatusFalse(Long id){
        TodoEntity toDoUpdated = todoDao.setStatusFalse(id);
        return mapper.toDto(toDoUpdated);
    }
}
