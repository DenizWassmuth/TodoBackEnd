package org.example.todobackend.controller;

import org.example.todobackend.enums.TodoStatus;
import org.example.todobackend.model.Todo;
import org.example.todobackend.repos.TodoRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // must-have for integration testing
@AutoConfigureMockMvc // must-have for integration testing
class TodoControllerTest {

    @Autowired // must-have for integration testing
    private MockMvc mockMvc;

    @Autowired
    private TodoRepo repo;

    @Test
    void getAllTodos_shouldReturnTheGivenJsonContent() throws Exception {

        // GIVEN
        Instant fakeTimestamp = Instant.parse("2018-04-01T00:00:00.00Z");
        String timeStampString = fakeTimestamp.toString();
        TodoStatus fakeStatus = TodoStatus.DOING;

        Todo newTodo = new Todo("1", "Putzen", "BodenWischen", fakeTimestamp, fakeStatus);
        repo.save(newTodo);

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json(        """
                                [
                                  {
                                    "id": "1",
                                    "title": "Putzen",
                                    "description": "BodenWischen",
                                    "timestamp": "%s",
                                    "status": "DOING"
                                  }
                                ]
                                """.formatted(timeStampString));

        //WHEN
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk()) // expecting "found" status
                .andExpect(jsonMatch);
    }


    @Test
    void getTodoById() throws Exception {

        // GIVEN
        Instant fakeTimestamp = Instant.parse("2018-04-01T00:00:00.00Z");
        String timeStampString = fakeTimestamp.toString();
        TodoStatus fakeStatus = TodoStatus.DOING;

        Todo newTodo = new Todo("1", "Putzen", "BodenWischen", fakeTimestamp, fakeStatus);
        repo.save(newTodo);

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json(        """
                                  {
                                    "id": "1",
                                    "title": "Putzen",
                                    "description": "BodenWischen",
                                    "timestamp": "%s",
                                    "status": "DOING"
                                  }
                                """.formatted(timeStampString));

        //WHEN
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/1"))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk()) // expecting "found" status
                .andExpect(jsonMatch);
    }

    @Test
    void createTodo() {
    }

    @Test
    void updateTodoById() {
    }

    @Test
    void deleteTodoById() {
    }
}