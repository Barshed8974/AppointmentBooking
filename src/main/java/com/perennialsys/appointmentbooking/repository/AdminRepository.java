package com.perennialsys.appointmentbooking.repository;

import com.perennialsys.appointmentbooking.entityClasses.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface AdminRepository extends JpaRepository<Admin,Long> {
    @Query("SELECT u FROM Admin u WHERE u.adminName = :n" )
    public Admin findByName(@Param("n") String name);

    @Query("SELECT u FROM Admin u WHERE u.uID = :n" )
    public Admin findByuId(@Param("n")String name);

}
