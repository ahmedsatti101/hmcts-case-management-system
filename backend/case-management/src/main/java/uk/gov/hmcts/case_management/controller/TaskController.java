package uk.gov.hmcts.case_management.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uk.gov.hmcts.case_management.dto.TaskRequest;
import uk.gov.hmcts.case_management.service.TaskService;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskRequest createNewTask(@Valid @RequestBody TaskRequest request) {
    service.createTask(request);
    return request;
  }
}
