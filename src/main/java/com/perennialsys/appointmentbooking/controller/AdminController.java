package com.perennialsys.appointmentbooking.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perennialsys.appointmentbooking.classDTO.DoctorDTO;
import com.perennialsys.appointmentbooking.classDTO.DoctorsScheduleDTO;
import com.perennialsys.appointmentbooking.entityClasses.*;
import com.perennialsys.appointmentbooking.exception.CustomException;
import com.perennialsys.appointmentbooking.service.AdminService;
import com.perennialsys.appointmentbooking.service.DoctorService;
import com.perennialsys.appointmentbooking.service.PatientService;
import com.perennialsys.appointmentbooking.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Component
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PatientService patientService;

    //addDoctor
    @PostMapping("/add-doctor")
    public Doctor addDoctor(@RequestBody DoctorDTO doctorDto, @RequestParam String token) throws Exception
    {
        return doctorService.addDoctor(doctorDto,token);
    } //Done


    @PostMapping("/schedule-doctor")
    public DoctorsSchedule scheduleDoctor(@RequestBody DoctorsScheduleDTO doctorsScheduleDTO, @RequestParam String token) throws Exception
    {
        return scheduleService.scheduleDoctor(doctorsScheduleDTO,token);
    } //Done


    //updateDoctor
    @PostMapping("/update-doctor")
    public Doctor updateDoctor(@RequestBody DoctorDTO doctorDto, @RequestParam String token)  throws Exception
    {
        return doctorService.updateDoctor(doctorDto,token);
    } //Done


    //deleteDoctor
    @GetMapping("/remove-doctor")
    public Doctor removeDoctor(@RequestParam ("id")Long doctorId, @RequestParam String token) throws Exception
    {
        return doctorService.removeDoctor(doctorId,token);
    } //Done


    //add admin
    public void addAdmin(Admin admin)
    {
        log.info("saving admin");
        if (admin !=null) {
            admin.setuID(admin.getuID()+ admin.getAdminId());
        }
    }


    //updateSchedule
    @PostMapping("/update-schedule")
    public DoctorsSchedule updateSchedule(@RequestBody DoctorsScheduleDTO doctorsScheduleDTO, @RequestParam String token) throws Exception
    {
        return scheduleDoctor(doctorsScheduleDTO,token);
    } //Done


    @GetMapping("/all-patient")
    public List<Patient> getAllPatient(@RequestParam String token) throws Exception
    {
        return patientService.getAllPatient(token);
    } //Done
}
