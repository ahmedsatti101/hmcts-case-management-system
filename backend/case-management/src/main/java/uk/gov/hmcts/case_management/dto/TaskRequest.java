package uk.gov.hmcts.case_management.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public record TaskRequest(
    int id,
    @NotNull String title,
    String description,
    @NotNull String status,
    @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime due) {}
