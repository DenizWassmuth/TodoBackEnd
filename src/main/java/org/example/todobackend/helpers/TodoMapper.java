package org.example.todobackend.helpers;


import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.model.TodoRecord;

import java.util.List;

public class TodoMapper {

    public static OutTodoDto mapToOutTodoDto(TodoRecord todoRecord) {
        if (todoRecord == null) {
            return null;
        }
        return new OutTodoDto(todoRecord.id(), todoRecord.title(), todoRecord.description(), todoRecord.timestamp());
    }

    public static List<OutTodoDto> mapToOutTodoDtoList(List<TodoRecord> todoRecords) {
        if (todoRecords == null) {
            return null;
        }
        return todoRecords.stream().map(TodoMapper::mapToOutTodoDto).toList();
    }
}
