package com.perennialsys.appointmentbooking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perennialsys.appointmentbooking.classDTO.DoctorsScheduleDTO;
import com.perennialsys.appointmentbooking.entityClasses.DoctorsSchedule;
import com.perennialsys.appointmentbooking.entityClasses.JwtBody;
import com.perennialsys.appointmentbooking.exception.CustomException;
import com.perennialsys.appointmentbooking.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@Slf4j
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    private boolean isAdmin=false;

    private CustomException customException=new CustomException();

    public DoctorsSchedule scheduleDoctor(DoctorsScheduleDTO doctorsScheduleDTO, String token) throws Exception
    {
        decodeJwt(token);
        customException.setMessage("invalid inputs");
        try
        {
            decodeJwt(token);
            if (doctorsScheduleDTO !=null&&isAdmin) {
                DoctorsSchedule doctorsSchedule=new DoctorsSchedule();
                doctorsSchedule.setDayOfWeek(doctorsScheduleDTO.getDayOfWeek());
                doctorsSchedule.setDoctorId(doctorsScheduleDTO.getDoctorId());
                doctorsSchedule.setEntryTime(doctorsScheduleDTO.getEntryTime());
                doctorsSchedule.setExitTime(doctorsScheduleDTO.getExitTime());
                return scheduleRepository.save(doctorsSchedule);
            }
            else
                throw customException;
        }
        catch (Exception e)
        {
            throw customException;
        }
    }
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
