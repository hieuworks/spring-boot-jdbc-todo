package com.example.todo.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SimplePropertyRowMapper;
import org.springframework.stereotype.Component;

import com.example.todo.dao.TodoCategoryDao;
import com.example.todo.domain.entity.TodoCategoryEntity;

@Component
public class TodoCategoryDaoImpl implements TodoCategoryDao {
    private static final SimplePropertyRowMapper<TodoCategoryEntity> ROW_MAPPER = new SimplePropertyRowMapper<>(TodoCategoryEntity.class);
    private final JdbcTemplate jdbcTemplate;

    public TodoCategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TodoCategoryEntity createTodoCategory(TodoCategoryEntity todoCategoryEntity) {
        String sql = """
                INSERT INTO todo_categories (name)
                VALUES (?)
                RETURNING id, name, created_at, updated_at, deleted_at
                """;

        return jdbcTemplate.queryForObject(
                sql,
                ROW_MAPPER,
                todoCategoryEntity.getName()
        );
    }

}
