package com.example.vacationpaycalculator.models.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class VacationPayRequestDTO {

    @NotNull(message = "The field 'averageSalary' should not be null")
    @Min(value = 0, message = "The field 'averageSalary' must be greater than or equal to 0")
    private Double averageSalary;

    @NotNull(message = "The field 'vacationDays' should not be null")
    @Min(value = 0, message = "The field 'vacationDays' must be greater than or equal to 0")
    private Integer vacationDays;

    @Null(message = "The field 'startDate' is optional and should be null when not provided")
    @FutureOrPresent(message = "The field 'startDate' must be in the future or today")
    private LocalDate startDate;

    @Null(message = "The field 'endDate' is optional and should be null when not provided")
    @Future(message = "The field 'endDate' must be a future date")
    private LocalDate endDate;
}


