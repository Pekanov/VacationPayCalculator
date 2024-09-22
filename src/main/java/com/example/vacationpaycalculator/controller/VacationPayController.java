package com.example.vacationpaycalculator.controller;

import com.example.vacationpaycalculator.models.dto.VacationPayRequestDTO;
import com.example.vacationpaycalculator.models.dto.VacationPayResponseDTO;
import com.example.vacationpaycalculator.service.VacationPayService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
@AllArgsConstructor
public class VacationPayController {

    private final VacationPayService vacationPayService;

    @GetMapping
    public VacationPayResponseDTO calculate(@Valid @RequestBody
                                            VacationPayRequestDTO vacationPayRequestDTO) {
        return vacationPayService.vacationPayResponseDTO(vacationPayRequestDTO);
    }


}
