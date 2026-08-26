package com.example.todo.mapper.impl;

import com.example.todo.domain.dto.TodoDto;
import com.example.todo.domain.entity.TodoEntity;
import com.example.todo.mapper.Mapper;
import org.springframework.stereotype.Component;


@Component
public class ToDoMapper implements Mapper<TodoEntity, TodoDto> {

    @Override
    public TodoEntity toEntity(TodoDto todoDto) {
        return TodoEntity.builder()
                .category_id(todoDto.getCategory_id())
                .title(todoDto.getTitle())
                .description(todoDto.getDescription())
                .build();
    }

    @Override
    public TodoDto toDto(TodoEntity todoEntity) {
        return TodoDto.builder()
                .category_id(todoEntity.getCategory_id())
                .title(todoEntity.getTitle())
                .description(todoEntity.getDescription())
                .status(todoEntity.getStatus())
                .build();
    }
}
