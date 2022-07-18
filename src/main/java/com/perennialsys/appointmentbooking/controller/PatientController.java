package com.perennialsys.appointmentbooking.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perennialsys.appointmentbooking.classDTO.BookAppointmentDTO;
import com.perennialsys.appointmentbooking.classDTO.CheckAvailabilityDTO;
import com.perennialsys.appointmentbooking.classDTO.PatientDTO;
import com.perennialsys.appointmentbooking.entityClasses.*;
import com.perennialsys.appointmentbooking.exception.CustomException;
import com.perennialsys.appointmentbooking.service.AppointmentService;
import com.perennialsys.appointmentbooking.service.DoctorService;
import com.perennialsys.appointmentbooking.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.swing.undo.CannotUndoException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Component
@RequestMapping("/patient")
@Slf4j
public class PatientController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    private boolean isPatient;


    //register
    @PostMapping("/register")
    public Patient register(@RequestBody PatientDTO patientDTO) throws CustomException
    {
        return patientService.register(patientDTO);
    } //Done


    //search doctor
    @GetMapping("/find-doctor-by-name")
    public List<Doctor> searchDoctorByName(@RequestParam("name") String doctorName) throws CustomException
    {
       return doctorService.searchByName(doctorName);
    } //Done


    //check availability
    @GetMapping("/check-availability")
    public Boolean checkAvailability(@RequestBody CheckAvailabilityDTO checkAvailabilityDTO) throws CustomException
    {
        if (checkAvailabilityDTO !=null) {
            return appointmentService.checkAvailability(checkAvailabilityDTO.getDoctorId(),
                    checkAvailabilityDTO.getLocalDate(),
                    checkAvailabilityDTO.getStartTime(),
                    checkAvailabilityDTO.getEndTime());
        }
        return false;
    }


    //book appointment
    @GetMapping("/book-appointment")
    public Appointment bookAppointment(@RequestBody BookAppointmentDTO bookAppointmentDTO, @RequestParam("token") String token) throws CustomException
    {
        return appointmentService.bookAppointment1(bookAppointmentDTO,token);
    } //Done


    //cancel appointment
    @GetMapping("/cancel-appointment")
    public Appointment cancelAppointment(@RequestParam("appointment-Id") Long appointmentId,@RequestParam String token)
            throws CustomException
    {
        return appointmentService.cancelAppointment(appointmentId,token);
    } //Done


    @GetMapping("/show-booked-slot")
    public List<Appointment> showBookedSlots(@RequestBody CheckAvailabilityDTO checkAvailabilityDTO)
    {
        if (checkAvailabilityDTO !=null&&isPatient) {
            return appointmentService.findByDoctorIdAndAppointmentDate(checkAvailabilityDTO.getDoctorId()
                    , checkAvailabilityDTO.getLocalDate());
        }
        else
            return new ArrayList<>();
    }


    @GetMapping("/all-doctors")
    public List<Doctor> showAllDoctor()
    {
        return doctorService.showAllDoctor();
    } //Done


    private void decodeJwt(String jwtToken)
    {
        log.debug("received token is "+jwtToken);
        ObjectMapper mapper = new ObjectMapper();
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        log.debug("decoding body");
        String body = new String(base64Url.decode(base64EncodedBody));
        log.debug("JWT Body : "+body);
        try {
            JwtBody emp2 = mapper.readValue(body, JwtBody.class);
            log.debug("trying json to java object");
            if (emp2.getSub().substring(0,3).equalsIgnoreCase("pat")&&emp2.getSub().length()>3)
                isPatient=true;
            log.debug("patient is "+isPatient);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.debug("error occurred");
        }
    }
}
