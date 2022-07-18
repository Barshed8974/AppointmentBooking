package com.perennialsys.appointmentbooking.service;

import com.perennialsys.appointmentbooking.entityClasses.Doctor;
import com.perennialsys.appointmentbooking.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DoctorServiceTest {

    private MockMvc mockMvc;
    @InjectMocks
    DoctorService doctorService;

    @Mock
    DoctorRepository doctorRepository=mock(DoctorRepository.class);

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(AdminService.class).build();
    }

    @Test
    void addDoctor() {
        Doctor doctor =new Doctor();
        doctor.setDoctorName("Ali");
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        //Doctor doctor1 =doctorService.addDoctor(doctor);
        //assertEquals("Ali", doctor1.getDoctorName());
    }

    @Test
    void updateDoctor() {
        Doctor doctor =new Doctor();
        doctor.setDoctorName("Ali");
        when(doctorRepository.save(doctor)).thenReturn(doctor);
//        Doctor doctor1 =doctorService.addDoctor(doctor);
//        assertEquals("Ali", doctor1.getDoctorName());
    }

    @Test
    void removeDoctor() {
        Doctor doctor =new Doctor();
        doctor.setDoctorName("Ali");
        when(doctorRepository.save(doctor)).thenReturn(doctor);
//        Doctor doctor1 =doctorService.removeDoctor(1L);
//        assertNull(doctor1.getDoctorName());
    }

//    @Test
//    void searchByName() {
//        DoctorDTO doctorDTO =new DoctorDTO();
//        doctorDTO.setDoctorName("Ali");
//        when(doctorRepository.findByName("Ali")).thenReturn(doctorDTO);
//        List<DoctorDTO> doctorDTO1 =doctorService.searchByName("Ali");
//        assertEquals("Ali", doctorDTO1.getDoctorName());
//    }
}