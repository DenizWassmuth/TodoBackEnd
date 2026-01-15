package org.example.todobackend.controller;

import jakarta.servlet.ServletRequest;
import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.dto.RegTodoDto;
import org.example.todobackend.dto.UpdateTodoDto;
import org.example.todobackend.exceptions.TodoNotFoundException;
import org.example.todobackend.model.Todo;
import org.example.todobackend.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
                //.orElseGet(() -> ResponseEntity.notFound().build())
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @PostMapping()
    public ResponseEntity<OutTodoDto> createTodo(@RequestBody RegTodoDto dto) {

        OutTodoDto createdTodo = todoService.createTodo(dto);

        // Build the URI of the created resource for the Location header
        URI location = URI.create("/api/todo/" + createdTodo.id());

        // 201 Created + Location + response body containing the createdTodo
        return ResponseEntity.created(location).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutTodoDto> updateTodoById(@PathVariable String id, @RequestBody UpdateTodoDto dto) {

        return Optional.of(todoService.updateTodoById(id, dto))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable String id) {

        boolean bStillExists = todoService.deleteTodoById(id);
        if (bStillExists) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }
}
