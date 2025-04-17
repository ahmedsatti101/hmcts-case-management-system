package uk.gov.hmcts.case_management;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import uk.gov.hmcts.case_management.controller.TaskController;
import uk.gov.hmcts.case_management.service.TaskService;

@WebMvcTest(TaskController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UnitTests {
  @Autowired private MockMvc mvc;

  @MockitoBean private TaskService service;

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
}
