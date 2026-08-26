package com.example.todo.controller;


import com.example.todo.domain.dto.TodoDto;
import com.example.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }
    @PostMapping(path = "api/v1/todo")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto request){
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

}
