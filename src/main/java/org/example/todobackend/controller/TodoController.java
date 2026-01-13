package org.example.todobackend.controller;

import jakarta.servlet.ServletRequest;
import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.dto.RegTodoDto;
import org.example.todobackend.dto.UpdateTodoDto;
import org.example.todobackend.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/todo")
public class TodoController {

    private final TodoService todoService;
    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping()
    public List<OutTodoDto> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutTodoDto> getTodoById(@PathVariable String id, ServletRequest servletRequest) {

        return Optional.of(todoService.getTodoById(id))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public OutTodoDto createTodo(@RequestBody RegTodoDto dto) {
        return todoService.createTodo(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutTodoDto> updateTodoById(@PathVariable String id, @RequestBody UpdateTodoDto dto) {
        return Optional.of(todoService.updateTodoById(id, dto))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public boolean deleteTodoById(@PathVariable String id) {
        return todoService.deleteTodoById(id);
    }
}
