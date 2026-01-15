package org.example.todobackend.exceptionhandling;


import org.example.todobackend.exceptions.TodoNotFoundException;
import org.example.todobackend.services.HelperService;
import org.example.todobackend.services.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @MockitoBean
    private HelperService helperService;

    @Test
    void whenTodoNotFound_thenGlobalExceptionHandlerReturns404() throws Exception {
        // given
        String missingId = "1";
        Instant now = Instant.now();

        // When controller calls todoService.getTodoById(missingId),
        // we want it to throw our custom exception.
        Mockito.when(helperService.getTimestamp()).thenReturn(now);

        Mockito.when(todoService.getTodoById(missingId))
                .thenThrow(new TodoNotFoundException(missingId));

        // when + then
        mockMvc.perform(get("/api/todo/{id}", missingId))
                // HTTP status should be 404
                .andExpect(status().isNotFound())
                // JSON body should contain the fields from GlobalExceptionHandler
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Todo with ID:" + missingId + " not found!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("http://localhost/api/todo/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }
}
