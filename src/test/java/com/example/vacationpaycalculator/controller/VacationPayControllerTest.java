package com.example.vacationpaycalculator.controller;

import com.example.vacationpaycalculator.models.dto.VacationPayRequestDTO;
import com.example.vacationpaycalculator.models.dto.VacationPayResponseDTO;
import com.example.vacationpaycalculator.service.VacationPayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest(VacationPayController.class)
class VacationPayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacationPayService vacationPayService;

    @Autowired
    private ObjectMapper objectMapper;

    private VacationPayRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // Инициализируем тестовые данные
        requestDTO = VacationPayRequestDTO.builder()
                .averageSalary(60000.0)
                .vacationDays(10)
                .build();
    }

    @Test
    void testCalculateVacationPay() throws Exception {
        // Мокаем ответ сервиса
        when(vacationPayService.vacationPayResponseDTO(Mockito.any(VacationPayRequestDTO.class)))
                .thenReturn(new VacationPayResponseDTO(20478.84, 10));

        // Отправляем POST-запрос к контроллеру
        mockMvc.perform(get("/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vacationPayAmount").value(20478.84))
                .andExpect(jsonPath("$.payableDays").value(10));

        // Проверяем, что метод сервиса был вызван один раз
        verify(vacationPayService, times(1)).vacationPayResponseDTO(any(VacationPayRequestDTO.class));
    }
}
