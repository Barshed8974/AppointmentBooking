package com.perennialsys.appointmentbooking.repository;

import com.perennialsys.appointmentbooking.entityClasses.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query("SELECT u FROM Patient u WHERE u.uID = :n" )
    public Patient findByuId(@Param("n")String uID);
}
