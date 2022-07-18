package com.perennialsys.appointmentbooking.service;

import com.perennialsys.appointmentbooking.entityClasses.Appointment;
import com.perennialsys.appointmentbooking.entityClasses.Doctor;
import com.perennialsys.appointmentbooking.entityClasses.DoctorsSchedule;
import com.perennialsys.appointmentbooking.entityClasses.Patient;
import com.perennialsys.appointmentbooking.repository.DoctorRepository;
import com.perennialsys.appointmentbooking.repository.PatientRepository;
import com.perennialsys.appointmentbooking.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppointmentServiceTest {

    private MockMvc mockMvc;
    @InjectMocks
    AppointmentService appointmentService;

    @Mock
    ScheduleRepository scheduleService=mock(ScheduleRepository.class);

    @Mock
    PatientRepository patientRepository=mock(PatientRepository.class);

    @Mock
    DoctorRepository doctorRepository=mock(DoctorRepository.class);

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(AppointmentService.class).build();
    }

    @Test
    void checkAvailability() {
        ArrayList<DoctorsSchedule> doctorsSchedules =new ArrayList<>();
        DoctorsSchedule doctorsSchedule =new DoctorsSchedule();
        doctorsSchedule.setDoctorId(1L);
        doctorsSchedule.setDayOfWeek(DayOfWeek.FRIDAY);
        doctorsSchedule.setEntryTime(LocalTime.of(10,00));
        doctorsSchedule.setExitTime(LocalTime.of(16,00));
        doctorsSchedules.add(doctorsSchedule);
        when(scheduleService.findByDoctorId(1L)).thenReturn(doctorsSchedules);
        Doctor doctor =new Doctor();
        doctor.setDoctorName("Arif");
        boolean status= appointmentService.checkAvailability(
                1L,
                LocalDate.of(2022,05,27),
                LocalTime.of(12,00),
                LocalTime.of(13,00)
        );
    }

//    @Test
//    void findByDoctorIdAndAppointmentDate() {
//        when(appointmentService.checkAvailability(1L,
//                LocalDate.of(2022,05,27),
//                LocalTime.of(12,00),
//                LocalTime.of(13,00))).thenReturn(true);
//        System.out.println(appointmentService.checkAvailability(1L,
//                LocalDate.of(2022,05,27),
//                LocalTime.of(12,00),
//                LocalTime.of(13,00)));
//        when(patientRepository.existsById(1L)).thenReturn(true);
//        Doctor doctor =new Doctor();
//        doctor.setDoctorName("johnson");
//        when(doctorRepository.findById(1L).get()).thenReturn(doctor);
//        Patient patient =new Patient();
//        patient.setPatientName("Ram");
//        when(patientRepository.findById(1L).get()).thenReturn(patient);
//        Appointment appointment=appointmentService.bookAppointment(
//                1L,
//                1L,
//                LocalDate.of(2022,05,27),
//                LocalTime.of(12,00),
//                LocalTime.of(13,00)
//        );
//        //assertEquals(doctor.getDoctorId(),appointment.getDoctorId());
//    }

    @Test
    void cancelAppointment() {
//        Appointment appointment=appointmentService.cancelAppointment(12L);
//        assertNull(appointment.getDoctorId());
    }
}