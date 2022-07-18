package com.perennialsys.appointmentbooking.repository;

import com.perennialsys.appointmentbooking.entityClasses.Appointment;
import com.perennialsys.appointmentbooking.entityClasses.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.ArrayList;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query("select u from DoctorsSchedule u where u.doctorId = :id and u.dayOfWeek = :date")
    ArrayList<Appointment> findByDoctorIdAndAppointmentDate(Doctor id, DayOfWeek date);
}