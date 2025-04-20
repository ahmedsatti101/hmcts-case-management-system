package uk.gov.hmcts.case_management;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.hmcts.case_management.dto.TaskRequest;
import uk.gov.hmcts.case_management.dto.UpdateTaskResource;
import uk.gov.hmcts.case_management.model.Task;
import uk.gov.hmcts.case_management.repository.TaskRepository;
import uk.gov.hmcts.case_management.service.TaskService;

@ExtendWith(MockitoExtension.class)
class ServiceUnitTests {
  @Mock
  private TaskRepository repository;

  private TaskService service;

  @BeforeEach
  void setup() {
    service = new TaskService(repository);
  }

  @Test
  void serviceCreatesTaskAndReturnsTask() {
    Task task = new Task();
    TaskRequest request = new TaskRequest("Get groceries", "Get milk, beef & cupcakes", "todo", LocalDateTime.of(2025, 4, 20, 10, 30, 0));

    task.setTitle(request.title());
    task.setDescription(request.description());
    task.setStatus(request.status());
    task.setDue(request.due());
 
    when(repository.save(any(Task.class))).thenReturn(task);

    Task savedTask = service.createTask(request);

    Assertions.assertThat(savedTask).isNotNull();
    Assertions.assertThat(savedTask.getTitle()).isEqualTo(request.title());
    Assertions.assertThat(savedTask.getDescription()).isEqualTo(request.description());
    Assertions.assertThat(savedTask.getStatus()).isEqualTo(request.status());
    Assertions.assertThat(savedTask.getDue()).isEqualTo(request.due());
  }

  @Test
  void serviceRetrievesTasksAndReturnsThem() {
    List<Task> tasks = new ArrayList<>();
    TaskService taskServiceMock = Mockito.mock(TaskService.class);

    tasks.add(new Task(null, "Task title", "task for testing", "Done", LocalDateTime.of(2025, 5, 2, 11, 32, 19)));

    when(taskServiceMock.retrieveAllTasks()).thenReturn(tasks);

    Assertions.assertThat(taskServiceMock.retrieveAllTasks()).isNotNull();
    Assertions.assertThat(taskServiceMock.retrieveAllTasks()).size().isEqualTo(1);
  }

  @Test
  void serviceRetrievesTaskByIdAndReturnsIt() {
    Optional<Task> newTask = Optional.of(new Task(null, "Test task", "Test task description", "Done", LocalDateTime.now()));
    TaskService taskServiceMock = Mockito.mock(TaskService.class);

    when(taskServiceMock.retrieveTaskById(newTask.get().getId())).thenReturn(newTask);

    Assertions.assertThat(taskServiceMock.retrieveTaskById(newTask.get().getId())).isNotNull();
  }

  @Test
  void serviceUpdatesTaskStatus() {
    Task originalTask = new Task(1L, "Code", "Finish coding assignment", "Todo", LocalDateTime.of(2025, 4, 28, 23, 55, 00));
    
    when(repository.findById(1L)).thenReturn(Optional.of(originalTask));
    
    when(repository.save(any(Task.class))).thenAnswer(invocation -> {
        return invocation.getArgument(0);
    });
    
    UpdateTaskResource updateRequest = new UpdateTaskResource(1L, "Pending...");
    
    Task updatedTask = service.updateTaskStatus(updateRequest);
    
    ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
    verify(repository).save(taskCaptor.capture());
    Task savedTask = taskCaptor.getValue();
    
    Assertions.assertThat(savedTask.getStatus()).isEqualTo("Pending...");
    Assertions.assertThat(updatedTask.getStatus()).isEqualTo("Pending...");
    
    Assertions.assertThat(savedTask.getId()).isEqualTo(1L);
    Assertions.assertThat(savedTask.getTitle()).isEqualTo("Code");
    Assertions.assertThat(savedTask.getDescription()).isEqualTo("Finish coding assignment");
  }
}
