package com.example.todo.mapper.impl;

import com.example.todo.domain.dto.TodoCategoryDto;
import com.example.todo.domain.dto.TodoDto;
import com.example.todo.domain.entity.TodoCategoryEntity;
import com.example.todo.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TodoCategoryMapper implements Mapper<TodoCategoryEntity, TodoCategoryDto> {

    @Override
    public TodoCategoryEntity toEntity(TodoCategoryDto todoCategoryDto) {
        return TodoCategoryEntity.builder()
                .name(todoCategoryDto.getName()).build();
    }

    @Override
    public TodoCategoryDto toDto(TodoCategoryEntity todoCategoryEntity) {
        return TodoCategoryDto.builder()
                .name(todoCategoryEntity.getName()).build();
    }
}
