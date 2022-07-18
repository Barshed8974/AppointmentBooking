package com.perennialsys.appointmentbooking.repository;


import com.perennialsys.appointmentbooking.entityClasses.DoctorsSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public interface ScheduleRepository extends JpaRepository<DoctorsSchedule,Long> {
    @Query("select u FROM DoctorsSchedule u WHERE u.doctorId=:n")
    public List<DoctorsSchedule> findByDoctorId(@Param("n")@RequestParam("id") Long id);
}
