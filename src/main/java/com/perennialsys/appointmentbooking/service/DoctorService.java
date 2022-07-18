package com.perennialsys.appointmentbooking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perennialsys.appointmentbooking.classDTO.DoctorDTO;
import com.perennialsys.appointmentbooking.entityClasses.Doctor;
import com.perennialsys.appointmentbooking.entityClasses.JwtBody;
import com.perennialsys.appointmentbooking.exception.CustomException;
import com.perennialsys.appointmentbooking.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    private boolean isAdmin=false;

    private CustomException customException=new CustomException();

    public Doctor addDoctor(DoctorDTO doctorDto,String jwtToken) throws Exception
    {
        customException.setMessage("invalid body or token");
        try{
            decodeJwt(jwtToken);
            if (doctorDto !=null&&isAdmin) {
                Doctor doctor=new Doctor();
                doctor.setDoctorName(doctorDto.getDoctorName());
                doctor.setDoctorSpeciality(doctorDto.getDoctorSpeciality());
                doctor.setArea(doctorDto.getArea());
                doctor.setPhoneNumber(doctorDto.getPhoneNumber());
                log.debug("adding doctor....!!!");
                doctorRepository.save(doctor);
                log.debug("successfully added....!!!");
                return doctor;
            }
            else
                throw customException;
        }
        catch (Exception e)
        {
            throw customException;
        }
    } //Done

    public Doctor updateDoctor( DoctorDTO doctorDto,  String token) throws Exception
    {
        customException.setMessage("invalid inputs");
        decodeJwt(token);
        try
        {
            if (doctorDto !=null&&isAdmin) {
                Doctor doctor=new Doctor();
                doctor.setDoctorName(doctorDto.getDoctorName());
                doctor.setDoctorSpeciality(doctorDto.getDoctorSpeciality());
                doctor.setArea(doctorDto.getArea());
                doctor.setPhoneNumber(doctorDto.getPhoneNumber());
                return doctorRepository.save(doctor);
            }
            else
                throw customException;
        }
        catch (Exception e)
        {
            throw customException;
        }
    } //Done


    public Doctor removeDoctor(Long doctorId,String token)  throws Exception
    {
        decodeJwt(token);
        customException.setMessage("id not found or invalid token");
        try
        {
            if (doctorRepository.findById(doctorId).isPresent()&&isAdmin)
            {
                Doctor doctor =doctorRepository.findById(doctorId).get();
                log.debug("deleting doctor....!!!");
                doctorRepository.deleteById(doctorId);
                return doctor;
            }
            else
                throw customException;
        }
        catch (Exception e)
        {
            throw customException;
        }
    } //Done


    public List<Doctor> showAllDoctor()
    {
        return doctorRepository.findAllDoctor();
    }

    public List<Doctor> searchByName(String doctorName) throws CustomException
    {
        customException.setMessage("invalid input or doctor not found");
        try
        {
            if (doctorName!=null)
            {
                List<Doctor> doctor =doctorRepository.findAllByDoctorName(doctorName);
                if (doctor !=null) {
                    return doctor;
                }
                else
                    throw customException;
            }
            else
            {
                throw customException;
            }
        }
        catch (Exception e)
        {
            throw customException;
        }
    } //Done
    private void decodeJwt(String jwtToken)
    {
        if (jwtToken!=null)
        {
            log.debug("received token is "+jwtToken);
            ObjectMapper mapper = new ObjectMapper();
            String[] splitString = jwtToken.split("\\.");
            String base64EncodedBody = splitString[1];
            Base64 base64Url = new Base64(true);
            log.debug("decoding body");
            String body = new String(base64Url.decode(base64EncodedBody));
            log.debug("JWT Body : "+body);
            try {
                JwtBody emp2 = mapper.readValue(body, JwtBody.class);
                log.debug("trying json to java object");
                if (emp2.getSub().substring(0,3).equalsIgnoreCase("adm"))
                    isAdmin=true;
                log.debug("admin is "+isAdmin);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.debug("error occurred");
            }
        }
        else
            isAdmin=false;
    }
}
