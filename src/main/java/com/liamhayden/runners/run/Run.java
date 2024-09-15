package com.liamhayden.runners.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Run(
                  Integer id,
                  @NotEmpty
                  String title,
                  LocalDateTime startOn,
                  LocalDateTime completedOn,
                  @Positive
                  Integer kilometers,
                  Location location
) {

    public Run {
        if(!completedOn.isAfter(startOn())) {
            throw new IllegalArgumentException("The start date must be after the end date");
        }
    }
}