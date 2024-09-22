package com.example.vacationpaycalculator.service;

import com.example.vacationpaycalculator.models.dto.VacationPayRequestDTO;
import com.example.vacationpaycalculator.models.dto.VacationPayResponseDTO;
import de.jollyday.HolidayManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class VacationPayService {

    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;
    private final HolidayManager holidayManager;


    public VacationPayResponseDTO vacationPayResponseDTO(VacationPayRequestDTO vacationPayRequestDTO) {
        VacationPayResponseDTO responseDTO = new VacationPayResponseDTO();
        responseDTO.setVacationPayAmount(calculateVacationPay(vacationPayRequestDTO.getAverageSalary(), vacationPayRequestDTO.getVacationDays()));
        if(vacationPayRequestDTO.getStartDate() != null
                &&vacationPayRequestDTO.getEndDate() != null) {
            responseDTO.setPayableDays(calculateWorkingDays(vacationPayRequestDTO.getStartDate(), vacationPayRequestDTO.getEndDate()));
        } else {
            responseDTO.setPayableDays(vacationPayRequestDTO.getVacationDays());
        }
        return responseDTO;
    }

    private double calculateVacationPay(double averageSalary, int vacationDays) {
        double dailySalary = averageSalary / AVERAGE_DAYS_IN_MONTH;
        return dailySalary * vacationDays;
    }

    private int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;

        while (!startDate.isAfter(endDate)) {
            if (!(startDate.getDayOfWeek().getValue() == 6
                    || startDate.getDayOfWeek().getValue() == 7
                    || holidayManager.isHoliday(startDate))) {
                workingDays++;
            }
            startDate = startDate.plusDays(1);
        }
        return workingDays;
    }

}
