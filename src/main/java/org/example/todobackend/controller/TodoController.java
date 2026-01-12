package org.example.todobackend.controller;

import org.example.todobackend.services.TodoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/todo")
public class TodoController {

    private final TodoService todoService;
    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
}
