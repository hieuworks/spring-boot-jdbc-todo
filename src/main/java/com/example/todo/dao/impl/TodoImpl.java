package com.example.todo.dao.impl;

import com.example.todo.dao.TodoDao;
import com.example.todo.domain.entity.TodoEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SimplePropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TodoImpl implements TodoDao {
    private static final SimplePropertyRowMapper<TodoEntity> ROW_MAPPER = new SimplePropertyRowMapper<>(TodoEntity.class);
    private final JdbcTemplate jdbcTemplate;

    public TodoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public TodoEntity createTodo(TodoEntity request) {
        String sql = """
                INSERT INTO todo
                (category_id,title, description)
                VALUES (?, ?, ?)
                RETURNING id, category_id, title, description, status, current_version, created_at, updated_at, deleted_at
                """;

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER,
                request.getCategory_id(),
                request.getTitle(),
                request.getDescription());
    }
}
