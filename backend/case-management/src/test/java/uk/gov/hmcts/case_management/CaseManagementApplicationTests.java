package uk.gov.hmcts.case_management;

import static org.hamcrest.Matchers.hasSize;

import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import io.restassured.RestAssured;
import uk.gov.hmcts.case_management.model.Task;
import uk.gov.hmcts.case_management.repository.TaskRepository;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CaseManagementApplicationTests {
  @ServiceConnection
  static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest");

  @LocalServerPort private int port;

  @BeforeAll
  static void beforeAll() {
    container.start();
  }

  @AfterAll
  static void afterAll() {
    container.stop();
  }

  @Autowired
  TaskRepository repository;

  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
    repository.deleteAll();
  }

  @Test
  void shouldCreateTask() {
    String body =
        """
          {
            "title": "Clean desk",
            "description": "I spilled coffee on my desk and it ruined my keyboard",
            "status": "Todo",
            "due": "2025-04-30T08:00:00"
        }
        """;

    RestAssured.given()
        .contentType("application/json")
        .body(body)
        .when()
        .post("/api/task")
        .then()
        .statusCode(201)
        .body("id", Matchers.notNullValue())
        .body("title", Matchers.equalTo("Clean desk"))
        .body("description", Matchers.equalTo("I spilled coffee on my desk and it ruined my keyboard"))
        .body("status", Matchers.equalTo("Todo"))
        .body("due", Matchers.equalTo("2025-04-30T08:00:00"));
  }

  @Test
  void shouldThrowErrorForInvalidRequestBody() {
    String body =
        """
          {
            "description": "I spilled coffee on my desk and it ruined my keyboard",
            "status": "Todo",
        }
        """;

    RestAssured.given()
        .contentType("application/json")
        .body(body)
        .when()
        .post("/api/task")
        .then()
        .statusCode(400);
  }

  @Test
  void shouldGetAllTasks() {
    List<Task> tasks = List.of(
      new Task(null, "Test task", null, "Todo", LocalDateTime.of(2025, 5, 3, 18, 30, 00)),
      new Task(null, "Test task", null, "Todo", LocalDateTime.of(2025, 5, 3, 18, 30, 00))
    );

    repository.saveAll(tasks);

    RestAssured.given()
      .contentType("application/json")
      .when()
      .get("/api/task")
      .then()
      .statusCode(200)
      .body(".", hasSize(2));
  }
}
