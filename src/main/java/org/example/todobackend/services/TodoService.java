package org.example.todobackend.services;


import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.dto.RegTodoDto;
import org.example.todobackend.dto.UpdateTodoDto;
import org.example.todobackend.enums.TodoStatus;
import org.example.todobackend.exceptions.TodoNotFoundException;
import org.example.todobackend.helpers.TodoMapper;
import org.example.todobackend.model.Todo;
import org.example.todobackend.repos.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.example.todobackend.helpers.TodoMapper.mapToOutTodoDto;

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
        return mapToOutTodoDto(Optional.of(todoRepo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id))).get());
    }

    //Post
    public OutTodoDto createTodo(RegTodoDto regTodoDto) {
        if (regTodoDto == null) {
            return null;
        }
        return mapToOutTodoDto(todoRepo.save(new Todo(
                helperService.createRandomId(),
                regTodoDto.title(), regTodoDto.description(),
                helperService.getTimestamp(),
                TodoStatus.DOING)));
    }

    //Put
    public OutTodoDto updateTodoById(String id, UpdateTodoDto updateTodoDto) {

        if (!todoRepo.existsById(id)) {
            throw new TodoNotFoundException(id);
        }

        return mapToOutTodoDto(todoRepo.save(todoRepo.findById(id).orElseThrow(() -> new TodoNotFoundException(id))
                .withTitle(updateTodoDto.title())
                .withDescription(updateTodoDto.description())
                .withStatus(updateTodoDto.status()))
        );
    }

    //Delete
    public boolean deleteTodoById(String id) {

        if(!todoRepo.existsById(id)) {
            throw new TodoNotFoundException(id);
        }

        todoRepo.deleteById(id);
        return todoRepo.existsById(id);
    }
}
