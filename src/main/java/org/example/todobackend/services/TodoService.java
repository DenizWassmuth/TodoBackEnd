package org.example.todobackend.services;


import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.dto.RegTodoDto;
import org.example.todobackend.dto.UpdateTodoDto;
import org.example.todobackend.enums.TodoStatus;
import org.example.todobackend.helpers.TodoMapper;
import org.example.todobackend.model.Todo;
import org.example.todobackend.repos.TodoRepo;
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
       return mapToOutTodoDtoList(todoRepo.findAll());
    }

    //Get
    public OutTodoDto getTodoById(String id) {
        if(id == null) { return null;}
        Optional<Todo> todo = todoRepo.findById(id);
        return todo.map(TodoMapper::mapToOutTodoDto).orElse(null);
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
        if(id == null) { return null; }

        if(!todoRepo.existsById(id)) {
            throw new IllegalArgumentException("Todo with id " + id + " does not exist");
        }

        Todo todo = todoRepo.findById(id).orElse(null);
           Todo updatedTodo = todo.withTitle(updateTodoDto.title())
                .withDescription(updateTodoDto.description())
                .withStatus(TodoStatus.valueOf(updateTodoDto.status()));

        return mapToOutTodoDto(updatedTodo);
    }

    //Delete
    public boolean deleteTodoById(String id) {
        if(id == null) { return false; }

        if(!todoRepo.existsById(id)) {
            throw new IllegalArgumentException("Todo with id " + id + " does not exist");
        }

        todoRepo.deleteById(id);
        return todoRepo.existsById(id);
    }
}
