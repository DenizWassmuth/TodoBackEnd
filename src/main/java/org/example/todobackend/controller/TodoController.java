package org.example.todobackend.controller;

import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.dto.RegTodoDto;
import org.example.todobackend.dto.UpdateTodoDto;
import org.example.todobackend.services.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public OutTodoDto getTodoById(@PathVariable String id) {
        return todoService.getTodoById(id);
    }

    @PostMapping()
    public OutTodoDto createTodo(@RequestBody RegTodoDto dto) {
        return todoService.createTodo(dto);
    }

    @PutMapping("/update/{id}")
    public OutTodoDto updateTodoById(@PathVariable String id, @RequestBody UpdateTodoDto dto) {
        return todoService.updateTodoById(id, dto);
    }

    @DeleteMapping
    public boolean deleteTodoById(@RequestParam String id) {
        return todoService.deleteTodoById(id);
    }
}
