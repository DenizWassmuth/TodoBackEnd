package org.example.todobackend.services;


import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.dto.RegTodoDto;
import org.example.todobackend.dto.UpdateTodoDto;
import org.example.todobackend.repos.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.todobackend.helpers.TodoMapper.mapToOutTodoDtoList;

@Service
public class TodoService {

    private final TodoRepo todoRepo;

    public TodoService(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    //Get
    List<OutTodoDto> getAllTodos() {
       return mapToOutTodoDtoList(todoRepo.findAll());
    }

    //Get
    OutTodoDto getTodoById(String id) {
        return null;
    }

    //Post
    OutTodoDto createTodo(RegTodoDto regTodoDto) {
        return null;
    }

    //Put
    OutTodoDto updateTodoById(String id, UpdateTodoDto updateTodoDto) {
        return null;
    }

    //Delete
    boolean deleteTodoById(String id) {
        return false;
    }
}
