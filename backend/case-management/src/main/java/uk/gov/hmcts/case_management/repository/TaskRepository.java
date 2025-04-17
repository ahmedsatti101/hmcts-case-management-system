package uk.gov.hmcts.case_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.case_management.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{}
