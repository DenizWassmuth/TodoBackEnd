package org.example.todobackend.services;

import org.example.todobackend.dto.OutTodoDto;
import org.example.todobackend.dto.RegTodoDto;
import org.example.todobackend.dto.UpdateTodoDto;
import org.example.todobackend.enums.TodoStatus;
import org.example.todobackend.model.Todo;
import org.example.todobackend.repos.TodoRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TodoServiceTest {

//    @Mock
//    TodoRepo todoRepo;
//
//    @InjectMocks
//    TodoService todoService;

    TodoRepo todoRepo =  Mockito.mock(TodoRepo.class);
    HelperService helperService =  Mockito.mock(HelperService.class);
    TodoService todoService = new TodoService(todoRepo, helperService);

    @Test
    void getAllTodos_shouldReturnNull_whenNoTodosFound() {

        //GIVEN
        Mockito.when(todoRepo.findAll()).thenReturn(null);

        //WHEN
        List<OutTodoDto> actualList = todoService.getAllTodos();

        //THEN
        Mockito.verify(todoRepo, Mockito.times(1)).findAll();
        assertNull(actualList);
    }

    @Test
    void getAllTodos_shouldReturnListOfSizeOne_containingGivenObject() {
        //Given
        Instant fakeTimestamp = Instant.parse("2018-04-01T00:00:00.00Z");
        TodoStatus fakeStatus = TodoStatus.DOING;

        Todo todo = new Todo("1", "Saubermachen", "Küche fegen, Bad putzen", fakeTimestamp, fakeStatus);
        List<Todo> toDoList = new ArrayList<>();
        toDoList.add(todo);

        Mockito.when(todoRepo.findAll()).thenReturn(toDoList);

        int expectedSize = 1;
        String expectedId = "1";

        //WHEN
        List<OutTodoDto> actualList = todoService.getAllTodos();

        //THEN
        Mockito.verify(todoRepo, Mockito.times(1)).findAll();
        assertEquals(expectedSize, actualList.size());
        assertEquals(expectedId, actualList.getFirst().id());
    }

    @Test
    void getTodoById_shouldReturnNull_whenNoTodoFound() {
        //GIVEN
        Mockito.when(todoRepo.findById("1")).thenReturn(Optional.empty());

        //WHEN
        OutTodoDto actualTodo = todoService.getTodoById("1");

        //THEN
        Mockito.verify(todoRepo, Mockito.times(1)).findById("1");
        assertNull(actualTodo);
    }

    @Test
    void getTodoById_shouldReturnGivenTodo_whenTodoFound() {

        //GIVEN
        Instant fakeTimestamp = Instant.parse("2018-04-01T00:00:00.00Z");
        TodoStatus fakeStatus = TodoStatus.DOING;
        Todo newTodo = new Todo("1", "Putzen", "Boden feucht wischen, Silber polieren", fakeTimestamp, fakeStatus);
        OutTodoDto expectedTodo = new OutTodoDto(newTodo.id(), newTodo.title(), newTodo.description(), fakeTimestamp, fakeStatus);
        Mockito.when(todoRepo.findById("1")).thenReturn(Optional.of(newTodo));

        //WHEN
        OutTodoDto actualTodo = todoService.getTodoById("1");

        //THEN
        Mockito.verify(todoRepo, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(todoRepo);

        assertNotNull(actualTodo);
        assertEquals(expectedTodo.id(), actualTodo.id());
        assertEquals(expectedTodo.title(), actualTodo.title());
        assertEquals(expectedTodo.description(), actualTodo.description());
        assertEquals(expectedTodo.timestamp(), actualTodo.timestamp());
        assertEquals(expectedTodo.status(), actualTodo.status());
    }

    @Test
    void createTodo_shouldReturnOutDtoVersionOfTodoObject() {

        //GIVEN
        String fakeId = "1";
        Instant fakeTimestamp = Instant.parse("2018-04-01T00:00:00.00Z");
        TodoStatus fakeStatus = TodoStatus.DOING;

        Todo newTodo = new Todo(fakeId, "Putzen", "Boden feucht wischen, Silber polieren", fakeTimestamp, fakeStatus);
        OutTodoDto expectedTodo = new OutTodoDto(newTodo.id(),  newTodo.title(), newTodo.description(), fakeTimestamp, fakeStatus);

        Mockito.when(todoRepo.save(newTodo)).thenReturn(newTodo);
        Mockito.when(helperService.createRandomId()).thenReturn(fakeId);
        Mockito.when(helperService.getNow()).thenReturn(fakeTimestamp);

        //WHEN
        OutTodoDto actualTodo = todoService.createTodo(new RegTodoDto(newTodo.title(), newTodo.description()));

        //THEN
        Mockito.verify(todoRepo, Mockito.times(1)).save(newTodo);
        Mockito.verifyNoMoreInteractions(todoRepo);
        Mockito.verify(helperService, Mockito.times(1)).createRandomId();
        Mockito.verify(helperService, Mockito.times(1)).getNow();
        Mockito.verifyNoMoreInteractions(helperService);

        assertNotNull(actualTodo);
        assertEquals(expectedTodo.id(), actualTodo.id());
        assertEquals(expectedTodo.title(), actualTodo.title());
        assertEquals(expectedTodo.description(), actualTodo.description());
        assertEquals(expectedTodo.timestamp(), actualTodo.timestamp());
        assertEquals(expectedTodo.status(), actualTodo.status());
    }

    @Test
    void updateTodoById_shouldReturnUpdatedVersionOfTodoObject() {
        //GIVEN
        String fakeId = "1";
        Instant fakeTimestamp = Instant.parse("2018-04-01T00:00:00.00Z");
        TodoStatus fakeStatus = TodoStatus.DOING;

        UpdateTodoDto updateDto = new UpdateTodoDto("Putzen", "einfach alles", TodoStatus.DONE);
        Todo updatedDto = new Todo(fakeId, updateDto.title(), updateDto.description(), fakeTimestamp, updateDto.status());

        OutTodoDto expectedTodo = new OutTodoDto(updatedDto.id(), updateDto.title(), updateDto.description(), fakeTimestamp, updateDto.status());

        Mockito.when(todoRepo.findById(fakeId)).thenReturn(Optional.of(updatedDto));
        Mockito.when(todoRepo.save(updatedDto)).thenReturn(updatedDto);
        Mockito.when(todoRepo.existsById(fakeId)).thenReturn(true);

        //WHEN
        OutTodoDto actualTodo = todoService.updateTodoById(fakeId, updateDto);

        //THEN
        Mockito.verify(todoRepo, Mockito.times(1)).existsById(fakeId);
        Mockito.verify(todoRepo, Mockito.times(1)).findById(fakeId);
        Mockito.verify(todoRepo, Mockito.times(1)).save(updatedDto);
        Mockito.verifyNoMoreInteractions(todoRepo);

        assertNotNull(actualTodo);
        assertEquals(expectedTodo.id(), actualTodo.id());
        assertEquals(expectedTodo.title(), actualTodo.title());
        assertEquals(expectedTodo.description(), actualTodo.description());
        assertEquals(expectedTodo.timestamp(), actualTodo.timestamp());
        assertEquals(expectedTodo.status(), actualTodo.status());
    }

    @Test
    void deleteTodoById() {

        //GIVEN
        Mockito.when(todoRepo.existsById("1")).thenReturn(true).thenReturn(false);

        //WHEN
        boolean actual = todoService.deleteTodoById("1");

        Mockito.verify(todoRepo, Mockito.times(2)).existsById("1");
        Mockito.verify(todoRepo, Mockito.times(1)).deleteById("1");
        Mockito.verifyNoMoreInteractions(todoRepo);

        assertFalse(actual);
    }
}