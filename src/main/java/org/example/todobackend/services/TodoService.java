package org.example.todobackend.services;


import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.dto.RegTodoDto;
import org.example.todobackend.dto.UpdateTodoDto;
import org.example.todobackend.enums.TodoStatus;
import org.example.todobackend.exceptions.TodoNotFoundException;
import org.example.todobackend.helpers.TodoMapper;
import org.example.todobackend.model.Todo;
import org.example.todobackend.repos.TodoRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.example.todobackend.helpers.TodoMapper.mapToOutTodoDto;
import static org.example.todobackend.helpers.TodoMapper.mapToOutTodoDtoList;

@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final HelperService helperService;
    public TodoService(TodoRepo todoRepo, HelperService helperService) {
        this.todoRepo = todoRepo;
        this.helperService = helperService;
    }

    //Get
    public List<OutTodoDto> getAllTodos() {
       return todoRepo.findAll().stream().map(TodoMapper::mapToOutTodoDto).toList();
    }

    //Get
    public OutTodoDto getTodoById(String id) {

        if(id == null) {
            return null;}

        Optional<Todo> todo = Optional.of(todoRepo.findById(id).orElseThrow(() -> new TodoNotFoundException(id)));
        Todo actualTodo = todo.orElse(null);

        return new OutTodoDto(actualTodo.id(), actualTodo.title(), actualTodo.description(), actualTodo.timestamp(), actualTodo.status());
    }

    //Post
    public OutTodoDto createTodo(RegTodoDto regTodoDto) {
        if (regTodoDto == null) { return null; }
        return mapToOutTodoDto(todoRepo.save(new Todo(
                helperService.createRandomId(),
                regTodoDto.title(), regTodoDto.description(),
                helperService.getNow(),
                TodoStatus.DOING)));
    }

    //Put
    public OutTodoDto updateTodoById(String id, UpdateTodoDto updateTodoDto) {
        if (id == null) {
            return null;
        }

        if (!todoRepo.existsById(id)) {
            throw new IllegalArgumentException("Todo with id " + id + " does not exist");
        }

        return mapToOutTodoDto(todoRepo.save(todoRepo.findById(id).orElse(null)
                .withTitle(updateTodoDto.title())
                .withDescription(updateTodoDto.description())
                .withStatus(updateTodoDto.status()))
        );
    }

    //Delete
    public boolean deleteTodoById(String id) {
        if(id == null) { return false; }

        if(!todoRepo.existsById(id)) {
            throw new TodoNotFoundException(id);
        }

        todoRepo.deleteById(id);
        return todoRepo.existsById(id);
    }
}
