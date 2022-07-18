package com.perennialsys.appointmentbooking.repository;

import com.perennialsys.appointmentbooking.entityClasses.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Component
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("SELECT u FROM Doctor u WHERE u.area = :n or u.doctorSpeciality=:sp")
    List<Doctor> findByParam(@Param("n")@RequestParam("area") String area, @Param("sp")@RequestParam("speciality")String speciality);
    @Query("DELETE  FROM Doctor u WHERE u.doctorName like '%n'")
    Doctor deleteByParam(@Param("n")@RequestParam("name") String name);

    @Query("select u FROM Doctor u WHERE u.doctorName =:n")
    List<Doctor> findAllByDoctorName(@Param("n") String name);

    @Query("select u FROM Doctor u ")
    ArrayList<Doctor> findAllDoctor();
}
