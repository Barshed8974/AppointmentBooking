package com.perennialsys.appointmentbooking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perennialsys.appointmentbooking.classDTO.PatientDTO;
import com.perennialsys.appointmentbooking.entityClasses.JwtBody;
import com.perennialsys.appointmentbooking.entityClasses.Patient;
import com.perennialsys.appointmentbooking.exception.CustomException;
import com.perennialsys.appointmentbooking.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    private boolean isAdmin=false;

    private final CustomException customException=new CustomException();

    public Patient register(PatientDTO patientDTO) throws CustomException
    {
        customException.setMessage("invalid inputs");
        try {
            if (patientDTO !=null) {
                Patient patient=new Patient();
                patient.setPatientName(patientDTO.getPatientName());
                patient.setPassword(patientDTO.getPassword());
                patient.setToDiagnose(patientDTO.getToDiagnose());
                Patient patient1=patientRepository.save(patient);
                patient1.setuID(patient.getuID()+patient.getPatientId());
                return patientRepository.save(patient1);
            }
            else
                throw customException;
        }
        catch (Exception e)
        {
            throw customException;
        }
    } //Done

    public Patient findById(long patientId) {
        log.debug("searching patient....!!!");
        if (patientRepository.findById(patientId).isPresent())
            return patientRepository.findById(patientId).get();
        else
            return new Patient();
    }

    public List<Patient> getAllPatient(String token) throws Exception
    {
        customException.setMessage("invalid token");
        try
        {
            decodeJwt(token);
            if (isAdmin)
            {
                return patientRepository.findAll();
            }
            else
                return new ArrayList<>();
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
    }
}
