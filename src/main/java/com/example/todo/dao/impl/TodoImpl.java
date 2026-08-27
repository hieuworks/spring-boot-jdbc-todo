package com.example.todo.dao.impl;

import com.example.todo.dao.TodoDao;
import com.example.todo.domain.entity.TodoEntity;
import com.example.todo.exception.ResourceNotFoundException;
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

    @Override
    public TodoEntity setStatusTrue(Long id) {
        String sql = """
                UPDATE todo
                SET status = ?
                WHERE id = ?
                """;
        int rowsAffected = jdbcTemplate.update(
                sql,
                true,
                id
        );
        if (rowsAffected == 0) {
            throw new ResourceNotFoundException("Todo not found");
        }
        return findById(id);
    }

    @Override
    public TodoEntity setStatusFalse(Long id) {
        String sql = """
                UPDATE todo
                SET status = ?
                WHERE id = ?
                """;
        int rowsAffected = jdbcTemplate.update(
                sql,
                false,
                id
        );
        if (rowsAffected == 0) {
            throw new ResourceNotFoundException("Todo not found");
        }
        return findById(id);
    }

    @Override
    public TodoEntity findById(Long id) {
        String sql = """
            SELECT *
            FROM todo
            WHERE id = ?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                ROW_MAPPER,
                id
        );
    }
}
