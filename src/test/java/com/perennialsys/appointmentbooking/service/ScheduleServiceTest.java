package com.perennialsys.appointmentbooking.service;

import com.perennialsys.appointmentbooking.entityClasses.DoctorsSchedule;
import com.perennialsys.appointmentbooking.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleServiceTest {

    private MockMvc mockMvc;
    @InjectMocks
    ScheduleService scheduleService;

    @Mock
    ScheduleRepository scheduleRepository=mock(ScheduleRepository.class);


    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(ScheduleService.class).build();
    }

    @Test
    void scheduleDoctor() {
        DoctorsSchedule doctorsSchedule =new DoctorsSchedule();
        doctorsSchedule.setDoctorId(10L);
        when(scheduleRepository.save(doctorsSchedule)).thenReturn(doctorsSchedule);
        //assertEquals(10L,scheduleService.scheduleDoctor(doctorsSchedule).getDoctorId());
    }
}