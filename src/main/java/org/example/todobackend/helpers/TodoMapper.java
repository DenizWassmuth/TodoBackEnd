package org.example.todobackend.helpers;


import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.model.Todo;

import java.util.List;

public class TodoMapper {

    public static OutTodoDto mapToOutTodoDto(Todo todo) {
        if (todo == null) {
            return null;
        }
        return new OutTodoDto(todo.id(), todo.title(), todo.description(), todo.timestamp(), todo.status());
    }

    public static List<OutTodoDto> mapToOutTodoDtoList(List<Todo> todos) {
        if (todos == null) {
            return null;
        }
        return todos.stream().map(TodoMapper::mapToOutTodoDto).toList();
    }
}
