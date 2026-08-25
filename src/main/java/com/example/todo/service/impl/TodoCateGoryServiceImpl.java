package com.example.todo.service.impl;

import com.example.todo.dao.impl.ToDoCategoryDaoImpl;
import com.example.todo.domain.dto.TodoCategoryDto;
import com.example.todo.domain.entity.TodoCategoryEntity;
import com.example.todo.mapper.impl.TodoCategoryMapper;
import com.example.todo.service.TodoCategoryService;
import org.springframework.stereotype.Service;

@Service
public class TodoCateGoryServiceImpl implements TodoCategoryService {
    private final ToDoCategoryDaoImpl toDoCategoryDao;
    private final TodoCategoryMapper mapper;

    public TodoCateGoryServiceImpl(ToDoCategoryDaoImpl toDoCategoryDao, TodoCategoryMapper mapper) {
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
