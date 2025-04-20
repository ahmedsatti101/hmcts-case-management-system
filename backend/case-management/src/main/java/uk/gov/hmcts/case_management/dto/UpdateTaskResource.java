package uk.gov.hmcts.case_management.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateTaskResource(@NotNull Long id, @NotNull String status) {}
