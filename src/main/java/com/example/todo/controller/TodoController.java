package com.example.todo.controller;


import com.example.todo.domain.dto.TodoDto;
import com.example.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("api/v1/{id}/complete")
    public ResponseEntity<TodoDto> updateTodoStatusTrue(@PathVariable Long id){
        return ResponseEntity.ok(service.setStatusTrue(id));
    }
    @PatchMapping("api/v1/{id}/un-complete")
    public ResponseEntity<TodoDto> updateTodoStatusFalse(@PathVariable Long id){
        return ResponseEntity.ok(service.setStatusFalse(id));
    }
}
