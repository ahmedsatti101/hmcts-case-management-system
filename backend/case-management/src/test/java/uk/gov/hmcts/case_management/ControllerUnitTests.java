package uk.gov.hmcts.case_management;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import uk.gov.hmcts.case_management.controller.TaskController;
import uk.gov.hmcts.case_management.dto.TaskRequest;
import uk.gov.hmcts.case_management.dto.UpdateTaskResource;
import uk.gov.hmcts.case_management.model.Task;
import uk.gov.hmcts.case_management.service.TaskService;

@WebMvcTest(TaskController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ControllerUnitTests {
  @Autowired private MockMvc mvc;

  @Autowired Optional<Task> task;

  @Autowired ObjectMapper objectMapper;

  @MockitoBean private TaskService service;

  @BeforeEach
  void setup() {
    task = Optional.of(new Task(null, "Test task", "Test description", "Todo", LocalDateTime.now()));
  }

  @Test
  void shouldCreateTask() throws Exception {
    String body =
        """
          {
            "title": "Clean desk",
            "description": "I spilled coffee on my desk and it ruined my keyboard",
            "status": "Todo",
            "due": "2025-04-30T08:00:00"
        }
        """;

    TaskRequest expectedRequest =
        new TaskRequest(
            "Clean desk",
            "I spilled coffee on my desk and it ruined my keyboard",
            "Todo",
            LocalDateTime.parse("2025-04-30T08:00:00"));

    Task mockTask = new Task();
    mockTask.setTitle("Clean desk");
    mockTask.setDescription("I spilled coffee on my desk and it ruined my keyboard");
    mockTask.setStatus("Todo");
    mockTask.setDue(LocalDateTime.parse("2025-04-30T08:00:00"));

    when(service.createTask(expectedRequest)).thenReturn(mockTask);

    mvc.perform(
            post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value("Clean desk"))
        .andExpect(
            jsonPath("$.description")
                .value("I spilled coffee on my desk and it ruined my keyboard"))
        .andExpect(jsonPath("$.status").value("Todo"))
        .andExpect(jsonPath("$.due").value("2025-04-30T08:00:00"));
  }

  @Test
  void shouldThrowErrorForInvalidRequestBody() throws Exception {
    String body =
        """
          {
            "title": "Clean desk",
            "description": "I spilled coffee on my desk and it ruined my keyboard",
            "due": "2025-04-30T08:00:00"
        }
        """;

    mvc.perform(
            post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnAllTasks() throws Exception {
    mvc.perform(get("/api/task"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void shouldReturnTaskWhenIdIsGiven() throws Exception {
    when(service.retrieveTaskById(1L)).thenReturn(task);

    MvcResult result = mvc.perform(get("/api/task/{id}", 1))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn();

    String response = result.getResponse().getContentAsString();

    Assertions.assertThat(response).isNotNull();
  }

  @Test
  void throwNotFoundIfTaskDoesNotExist() throws Exception {
    mvc.perform(get("/api/task/{id}", 100))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message").value("Task not found"));
  }

  @Test
  void throwBadRequestIfIdIsInvalid() throws Exception {
    mvc.perform(get("/api/task/banana"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message").value("ID must be a number"));
  }

  @Test
  void shouldUpdateTaskStatus() throws Exception {
    Task updated = new Task(1L, "Test task", "Test task description", "Todo", LocalDateTime.now());
    when(service.updateTaskStatus(new UpdateTaskResource(1L, "Done"))).thenReturn(updated);

    MvcResult result = mvc.perform(patch("/api/task/{id}", 1)
    .content(objectMapper.writeValueAsString(new UpdateTaskResource(1L, "Done")))
    .characterEncoding("UTF-8")
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
    .andReturn();

    String response = result.getResponse().getContentAsString();

    Assertions.assertThat(response).isNotNull();
  }

  @Test
  void throwBadRequestIfRequestBodyIsEmpty() throws Exception {
    mvc.perform(patch("/api/task/1"))
      .andExpect(status().isBadRequest());
  }

  @Test
  void throwNotFoundIfTaskNotFoundForPatchRequest() throws Exception {
    mvc.perform(patch("/api/task/100")
    .content(objectMapper.writeValueAsString(new UpdateTaskResource(100L, "Complete")))
    .contentType(MediaType.APPLICATION_JSON_VALUE)
    .characterEncoding("UTF-8")
    .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message").value("Task not found"));
  }
}
