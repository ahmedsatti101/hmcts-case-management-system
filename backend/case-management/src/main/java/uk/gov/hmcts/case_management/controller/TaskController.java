package uk.gov.hmcts.case_management.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import uk.gov.hmcts.case_management.dto.TaskRequest;
import uk.gov.hmcts.case_management.dto.UpdateTaskResource;
import uk.gov.hmcts.case_management.exception.ErrorObject;
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
    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ErrorObject.class), mediaType = "application/json")}),
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Task createNewTask(@Valid @RequestBody TaskRequest request) {
    return service.createTask(request);
  }

  @Operation(summary = "Get all tasks")
  @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json")})
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Task> getAllTasks() {
    return service.retrieveAllTasks();
  }

  @Operation(summary = "Get task by ID")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))}),
    @ApiResponse(responseCode = "404", description = "Task not found", content = {@Content(schema = @Schema(implementation = ErrorObject.class), mediaType = "application/json")}),
    @ApiResponse(responseCode = "400", description = "Invalid ID provided", content = {@Content(schema = @Schema(implementation = ErrorObject.class), mediaType = "application/json")}),
  })
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Optional<Task> getTask(@Parameter(description = "Task id", required = true) @PathVariable Long id) {
    if (service.retrieveTaskById(id).isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

    return service.retrieveTaskById(id);
  }

  @Operation(summary = "Update task status")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))}),
    @ApiResponse(responseCode = "404", description = "Task not found", content = {@Content(schema = @Schema(implementation = ErrorObject.class), mediaType = "application/json")}),
    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ErrorObject.class), mediaType = "application/json")}),
  })
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Task updateTask(@Valid @RequestBody UpdateTaskResource resource) {
    if (service.retrieveTaskById(resource.id()).isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

    return service.updateTaskStatus(resource);
  }

  @Operation(summary = "Delete task")
  @ApiResponses({
    @ApiResponse(responseCode = "404", description = "Task not found", content = {@Content(schema = @Schema(implementation = ErrorObject.class), mediaType = "application/json")}),
    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ErrorObject.class), mediaType = "application/json")}),
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public String deleteTask(@Parameter(required = true, description = "Task id") @PathVariable Long id) {
    if (service.retrieveTaskById(id).isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

    service.deleteRequestedTask(id);
    return "Task deleted successfully";
  }
}
