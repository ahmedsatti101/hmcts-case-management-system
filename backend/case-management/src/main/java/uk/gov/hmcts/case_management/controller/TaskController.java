package uk.gov.hmcts.case_management.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import uk.gov.hmcts.case_management.dto.TaskRequest;
import uk.gov.hmcts.case_management.model.Task;
import uk.gov.hmcts.case_management.service.TaskService;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Tag(name = "Task endpoints")
public class TaskController {
  private final TaskService service;

  @Operation(summary = "Create new task")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))
        }),
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Task createNewTask(@Valid @RequestBody TaskRequest request) {
    return service.createTask(request);
  }
}
