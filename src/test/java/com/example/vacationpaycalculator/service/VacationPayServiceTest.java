package com.example.vacationpaycalculator.service;

import com.example.vacationpaycalculator.models.dto.VacationPayRequestDTO;
import com.example.vacationpaycalculator.models.dto.VacationPayResponseDTO;
import de.jollyday.HolidayManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VacationPayServiceTest {

    @Mock
    private HolidayManager holidayManager;

    @InjectMocks
    private VacationPayService vacationPayService;

    @Test
    public void testCalculateVacationPay_WithValidInput() {
        VacationPayRequestDTO request = new VacationPayRequestDTO();
        request.setAverageSalary(120000.0);
        request.setVacationDays(14);

        VacationPayResponseDTO response = vacationPayService.vacationPayResponseDTO(request);

        double expectedVacationPay = (120000 / 29.3) * 14;
        assertEquals(expectedVacationPay, response.getVacationPayAmount(), 0.01);
        assertEquals(14, response.getPayableDays());
    }

    @Test
    public void testCalculateWorkingDays_WithWeekendsAndHolidays() {
        VacationPayRequestDTO request = new VacationPayRequestDTO();
        request.setAverageSalary(120000.0);
        request.setVacationDays(10);
        request.setStartDate(LocalDate.of(2024, 5, 1));
        request.setEndDate(LocalDate.of(2024, 5, 10));

        when(holidayManager.isHoliday(any(LocalDate.class))).thenReturn(false);

        when(holidayManager.isHoliday(LocalDate.of(2024, 5, 1))).thenReturn(true);
        when(holidayManager.isHoliday(LocalDate.of(2024, 5, 9))).thenReturn(true);

        VacationPayResponseDTO response = vacationPayService.vacationPayResponseDTO(request);

        int expectedWorkingDays = 6;
        assertEquals(expectedWorkingDays, response.getPayableDays());
    }

    @Test
    public void testCalculateWorkingDays_WithoutHolidays() {
        VacationPayRequestDTO request = new VacationPayRequestDTO();
        request.setAverageSalary(90000.0);
        request.setVacationDays(10);
        request.setStartDate(LocalDate.of(2024, 5, 13));
        request.setEndDate(LocalDate.of(2024, 5, 22));

        when(holidayManager.isHoliday(any(LocalDate.class))).thenReturn(false);

        VacationPayResponseDTO response = vacationPayService.vacationPayResponseDTO(request);

        int expectedWorkingDays = 8;
        assertEquals(expectedWorkingDays, response.getPayableDays());
    }

    @Test
    public void testCalculateVacationPay_WithoutWorkingDays() {
        VacationPayRequestDTO request = new VacationPayRequestDTO();
        request.setAverageSalary(100000.0);
        request.setVacationDays(10);

        VacationPayResponseDTO response = vacationPayService.vacationPayResponseDTO(request);

        double expectedVacationPay = (100000 / 29.3) * 10;
        assertEquals(expectedVacationPay, response.getVacationPayAmount(), 0.01);
    }
}