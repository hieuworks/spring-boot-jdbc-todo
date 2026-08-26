package com.example.todo.service.impl;

import com.example.todo.dao.TodoCategoryDao;
import com.example.todo.domain.dto.TodoCategoryDto;
import com.example.todo.domain.entity.TodoCategoryEntity;
import com.example.todo.mapper.Mapper;
import com.example.todo.service.TodoCategoryService;
import org.springframework.stereotype.Service;

@Service
public class TodoCateGoryServiceImpl implements TodoCategoryService {
    private final TodoCategoryDao toDoCategoryDao;
    private final Mapper<TodoCategoryEntity, TodoCategoryDto> mapper;

    public TodoCateGoryServiceImpl(TodoCategoryDao toDoCategoryDao, Mapper<TodoCategoryEntity, TodoCategoryDto> mapper) {
        this.toDoCategoryDao = toDoCategoryDao;
        this.mapper = mapper;
    }

    @Override
    public TodoCategoryDto create(TodoCategoryDto request) {
        TodoCategoryEntity entity = mapper.toEntity(request);
        TodoCategoryEntity savedEntity = toDoCategoryDao.createTodoCategory(entity);
        return mapper.toDto(savedEntity);
    }
}
