package com.example.vacationpaycalculator.models.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class VacationPayResponseDTO {

    private double vacationPayAmount;
    private int payableDays;

}
