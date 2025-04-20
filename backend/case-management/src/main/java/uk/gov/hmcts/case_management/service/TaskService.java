package uk.gov.hmcts.case_management.service;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uk.gov.hmcts.case_management.dto.TaskRequest;
import uk.gov.hmcts.case_management.dto.UpdateTaskResource;
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

  public List<Task> retrieveAllTasks() {
    return repository.findAll();
  }

  public Optional<Task> retrieveTaskById(Long id) {
    return repository.findById(id);
  }

  public Task updateTaskStatus(@Valid UpdateTaskResource resource) {
    Task taskToBeUpdated = null;

    if (repository.findById(resource.id()).isPresent()) {
      taskToBeUpdated = repository.findById(resource.id()).get();
    }

    taskToBeUpdated.setStatus(resource.status());
    return repository.save(taskToBeUpdated);
  }
}
