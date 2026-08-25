package com.example.todo.controller;

import com.example.todo.domain.dto.TodoCategoryDto;
import com.example.todo.domain.entity.TodoCategoryEntity;
import com.example.todo.service.TodoCategoryService;
import jdk.jfr.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoCategoryController {
    private final TodoCategoryService service;

    public ToDoCategoryController(TodoCategoryService service) {
        this.service = service;
    }

    @PostMapping(path = "/category")
     public ResponseEntity<TodoCategoryDto> createCategory(@RequestBody TodoCategoryDto todoCategoryDto){
        return new ResponseEntity<>(service.create(todoCategoryDto), HttpStatus.CREATED);
    }
}
