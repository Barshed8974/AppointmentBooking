package com.perennialsys.appointmentbooking.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perennialsys.appointmentbooking.classDTO.BookAppointmentDTO;
import com.perennialsys.appointmentbooking.classDTO.CheckAvailabilityDTO;
import com.perennialsys.appointmentbooking.entityClasses.Appointment;
import com.perennialsys.appointmentbooking.entityClasses.DoctorsSchedule;
import com.perennialsys.appointmentbooking.entityClasses.JwtBody;
import com.perennialsys.appointmentbooking.exception.CustomException;
import com.perennialsys.appointmentbooking.repository.AppointmentRepository;
import com.perennialsys.appointmentbooking.repository.DoctorRepository;
import com.perennialsys.appointmentbooking.repository.PatientRepository;
import com.perennialsys.appointmentbooking.repository.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AppointmentService {
    @Autowired
    private ScheduleRepository scheduleService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    private boolean isPatient =false;

    private final CustomException customException=new CustomException();

    public Boolean checkAvailability(Long doctorId,
                                     LocalDate date, LocalTime startTime,
                                     LocalTime endTime)
    {
        DayOfWeek dayOfWeek=date.getDayOfWeek();
        log.debug(dayOfWeek.toString());
        boolean scheduledByDay=checkAvailabilityByDay(date,doctorId);
        boolean scheduledByTime=checkAvailabilityByTime(scheduledByDay,doctorId,date,startTime,endTime);
        return scheduledByDay && scheduledByTime;
    }



    public List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate date) {
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorRepository.findById(doctorId).get(),date.getDayOfWeek());
    }


    public Appointment bookAppointment( long doctorId, long patientId,
                                       LocalDate date, LocalTime startTime,
                                        LocalTime endTime)
    {
        DayOfWeek dayOfWeek=date.getDayOfWeek();
        log.debug("booking appointment ....!!!");

        if (Boolean.TRUE.equals(checkAvailability(doctorId,date, startTime,endTime))
                &&patientRepository.existsById(patientId))
        {
            Appointment appointment=new Appointment();
            appointment.setAppointmentDate(date);
            appointment.setDayOfWeek(dayOfWeek);
            appointment.setStartTime(startTime);
            log.debug("startTime = "+startTime);
            appointment.setEndTime(endTime);
            log.debug("endTime = "+endTime);
            appointment.setDoctorId(doctorRepository.findById(doctorId).get());
            appointment.setPatientId(patientRepository.findById(patientId).get());
            appointmentRepository.save(appointment);
            log.debug("appointment booked successfully...!!!");
            return appointment;
        }
        else {
            log.debug("failed to book an appointment ...!!!");
            return new Appointment();
        }
    }

    public Boolean checkAvailability1(CheckAvailabilityDTO checkAvailabilityDTO) throws CustomException
    {
        customException.setMessage("invalid input");
        try {
            DayOfWeek dayOfWeek=checkAvailabilityDTO.getLocalDate().getDayOfWeek();
            log.debug(dayOfWeek.toString());
            boolean scheduledByDay=checkAvailabilityByDay(checkAvailabilityDTO.getLocalDate(),checkAvailabilityDTO.getDoctorId());
            boolean scheduledByTime=checkAvailabilityByTime(scheduledByDay,checkAvailabilityDTO.getDoctorId()
                    ,checkAvailabilityDTO.getLocalDate()
                    ,checkAvailabilityDTO.getStartTime()
                    ,checkAvailabilityDTO.getEndTime());
            return scheduledByDay && scheduledByTime;
        }
        catch (Exception e)
        {
            throw customException;
        }
    } //Done


    public Appointment bookAppointment1(BookAppointmentDTO bookAppointmentDTO,String token) throws CustomException
    {
        customException.setMessage("invalid input");
        decodeJwt(token);
        try{
            if (isPatient)
            {

                DayOfWeek dayOfWeek=bookAppointmentDTO.getLocalDate().getDayOfWeek();
                log.debug("booking appointment ....!!!");

                if (Boolean.TRUE.equals(checkAvailability1( new CheckAvailabilityDTO(bookAppointmentDTO.getDoctorId(),
                        bookAppointmentDTO.getLocalDate(),bookAppointmentDTO.getStartTime()
                        ,bookAppointmentDTO.getEndTime()))
                        &&patientRepository.existsById(bookAppointmentDTO.getPatientId())))
                {
                    Appointment appointment=new Appointment();
                    appointment.setAppointmentDate(bookAppointmentDTO.getLocalDate());
                    appointment.setDayOfWeek(dayOfWeek);
                    appointment.setStartTime(bookAppointmentDTO.getStartTime());
                    appointment.setEndTime(bookAppointmentDTO.getEndTime());
                    appointment.setDoctorId(doctorRepository.findById(bookAppointmentDTO.getDoctorId()).get());
                    appointment.setPatientId(patientRepository.findById(bookAppointmentDTO.getPatientId()).get());
                    appointmentRepository.save(appointment);
                    log.debug("appointment booked successfully...!!!");
                    return appointment;
                }
                else {
                    log.debug("failed to book an appointment ...!!!");
                    throw customException;
                }
            }
            else {
                throw customException;
            }
        }
        catch (Exception e)
        {
            throw customException;
        }
    }

    public Appointment cancelAppointment( Long appointmentId,String token) throws CustomException
    {
        customException.setMessage("invalid inputs");
        decodeJwt(token);
        try {
            if (isPatient)
            {
                Appointment appointment=appointmentRepository.findById(appointmentId).get();
                log.debug("deleting appointment....!!!");
                appointmentRepository.deleteById(appointmentId);
                return appointment;
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
    }

    public boolean checkAvailabilityByDay(LocalDate localDate, long doctorId)
    {
        DayOfWeek dayOfWeek=localDate.getDayOfWeek();
        log.debug(dayOfWeek.toString());
        boolean scheduledByDay=false;
        List<DoctorsSchedule> doctorsSchedules =scheduleService.findByDoctorId(doctorId);
        log.debug("size of list = " + doctorsSchedules.size());
        if (localDate.isAfter(LocalDate.now()))
        {
            log.debug("date.isAfter(LocalDate.now()) = "+localDate.isAfter(LocalDate.now()));
            for (DoctorsSchedule schedule: doctorsSchedules
            ) {
                log.debug("days = "+schedule.getDayOfWeek());
                if (dayOfWeek.equals(schedule.getDayOfWeek()))
                {
                    scheduledByDay=true;
                    break;
                }
            }
        }
        return scheduledByDay;
    }

    public boolean checkAvailabilityByTime(boolean scheduledByDay,Long doctorId,
                                           LocalDate date,LocalTime startTime, LocalTime endTime
                                            )
    {
        boolean scheduledByTime=true;

        if (Boolean.TRUE.equals(scheduledByDay))
        {
            ArrayList<Appointment> appointmentArrayList=new ArrayList<>();
            if (doctorRepository.findById(doctorId).isPresent())
            {
                appointmentArrayList= appointmentRepository.
                        findByDoctorIdAndAppointmentDate(doctorRepository.findById(doctorId).get(),date.getDayOfWeek());
            }
            for (Appointment appointment:appointmentArrayList
            ) {
                if (appointment.getStartTime().isBefore(startTime)||appointment.getEndTime().isAfter(startTime)
                        ||appointment.getStartTime().isBefore(endTime)||appointment.getEndTime().isAfter(endTime)
                )
                {
                    scheduledByTime=false;
                    break;
                }
            }

        }
        return scheduledByTime;
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
                if (emp2.getSub().substring(0,3).equalsIgnoreCase("pat"))
                    isPatient =true;
                log.debug("patient is "+ isPatient);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.debug("error occurred");
            }
        }
        else
            isPatient =false;
    }
}
