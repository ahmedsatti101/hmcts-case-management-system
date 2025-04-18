package uk.gov.hmcts.case_management.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uk.gov.hmcts.case_management.dto.TaskRequest;
import uk.gov.hmcts.case_management.model.Task;
import uk.gov.hmcts.case_management.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository repository;

  public Task createTask(TaskRequest request) {
    Task newTask = new Task();

    newTask.setTitle(request.title());
    newTask.setDescription(request.description());
    newTask.setStatus(request.status());
    newTask.setDue(request.due());

    return repository.save(newTask);
  }
}
