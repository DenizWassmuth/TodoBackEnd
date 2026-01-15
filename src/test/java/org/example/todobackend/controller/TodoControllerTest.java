package org.example.todobackend.controller;

import org.example.todobackend.enums.TodoStatus;
import org.example.todobackend.exceptions.TodoNotFoundException;
import org.example.todobackend.model.Todo;
import org.example.todobackend.repos.TodoRepo;
import org.example.todobackend.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // must-have for integration testing
@AutoConfigureMockMvc // must-have for integration testing
class TodoControllerTest {

    @Autowired // must-have for integration testing
    private MockMvc mockMvc;

    @Autowired
    private TodoRepo repo;

    @BeforeEach
    void cleanDb() {
        repo.deleteAll(); // ensures every test starts from empty DB
    }

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
        ResultActions resultActions = mockMvc.perform(get("/api/todo"))
                //THEN
                .andExpect(status().isOk()) // expecting "found" status
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

        ResultMatcher jsonMatch = MockMvcResultMatchers.content().json(
                                """
                                  {
                                    "id": "1",
                                    "title": "Putzen",
                                    "description": "BodenWischen",
                                    "timestamp": "%s",
                                    "status": "DOING"
                                  }
                                """.formatted(timeStampString));

        //WHEN
        mockMvc.perform(get("/api/todo/1"))
                //THEN
                .andExpect(status().isOk()) // expecting "found" status
                .andExpect(jsonMatch);
    }


    @Test
    void createTodo() throws Exception {

        // WHEN/THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                              """
                              {
                                "title": "Putzen",
                                "description": "Boden wischen",
                                "status": "DOING"
                               }
                              """))
                //.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Putzen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Boden wischen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DOING"));
    }

    @Test
    void updateTodoById() throws Exception {

        //GIVEN
        Instant fakeTimestamp = Instant.parse("2018-04-01T00:00:00.00Z");
        String timeStampString = fakeTimestamp.toString();
        TodoStatus fakeStatus = TodoStatus.DOING;

        Todo newTodo = new Todo("1", "Waschen", "weiße Wäsche", fakeTimestamp, fakeStatus);
        repo.save(newTodo);

        // WHEN/THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                  "title": "Putzen",
                                  "description": "Boden wischen",
                                  "status": "DONE"
                                 }
                                """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Putzen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Boden wischen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DONE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").value(timeStampString));

    }

    @Test
    void deleteTodoById() throws Exception {

        // GIVEN
        Instant fakeTimestamp = Instant.parse("2018-04-01T00:00:00.00Z");
        TodoStatus fakeStatus = TodoStatus.DOING;

        Todo newTodo = new Todo("1", "Putzen", "BodenWischen", fakeTimestamp, fakeStatus);
        repo.save(newTodo);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/1"))
                //THEN
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}