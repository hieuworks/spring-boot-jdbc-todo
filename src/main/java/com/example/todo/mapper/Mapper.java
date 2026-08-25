package com.example.todo.mapper;

import com.example.todo.domain.dto.TodoCategoryDto;
import com.example.todo.domain.entity.TodoCategoryEntity;

public interface Mapper<A, B> {
    public A toEntity (B b);
    public B toDto (A a);
}
