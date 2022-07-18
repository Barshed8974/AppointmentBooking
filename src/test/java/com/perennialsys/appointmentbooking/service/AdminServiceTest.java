package com.perennialsys.appointmentbooking.service;

import com.perennialsys.appointmentbooking.controller.AdminController;
import com.perennialsys.appointmentbooking.entityClasses.Admin;
import com.perennialsys.appointmentbooking.repository.AdminRepository;
import com.perennialsys.appointmentbooking.repository.PatientRepository;
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

class AdminServiceTest {

    private MockMvc mockMvc;
    @InjectMocks
    AdminService adminService;

    @Mock
    AdminRepository adminRepository=mock(AdminRepository.class);

    @Mock
    PatientRepository patientRepository=mock(PatientRepository.class);

    @Mock
    AdminController adminController=mock(AdminController.class);

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(AdminService.class).build();
    }

    @Test
    void save() {
        Admin admin =new Admin();
        admin.setAdminId(12L);
        admin.setAdminName("ali");
        admin.setAdminPassword("abc");
        when(adminService.save(admin)).thenReturn(admin);
        assertEquals("ali", admin.getAdminName());
    }
}