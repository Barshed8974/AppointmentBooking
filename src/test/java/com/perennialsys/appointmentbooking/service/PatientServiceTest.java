package com.perennialsys.appointmentbooking.service;

import com.perennialsys.appointmentbooking.entityClasses.Patient;
import com.perennialsys.appointmentbooking.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientServiceTest {

    private MockMvc mockMvc;
    @InjectMocks
    PatientService patientService;

    @Mock
    PatientRepository patientRepository=mock(PatientRepository.class);


    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(PatientService.class).build();
    }

    @Test
    void register() {
        Patient patient =new Patient();
        patient.setPatientName("Ali");
        when(patientRepository.save(patient)).thenReturn(patient);
        //Patient patient1 =patientService.register(patient);
        //assertEquals("Ali", patient1.getPatientName());
    }

    @Test
    void findById() {
        Patient patient =new Patient();
        patient.setPatientName("Ali");
        when(patientRepository.findById(12L)).thenReturn(Optional.of(patient));
        Patient patient1 =patientService.findById(12);
        assertEquals("Ali", patient1.getPatientName());
    }
}