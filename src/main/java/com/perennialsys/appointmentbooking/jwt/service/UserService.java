package com.perennialsys.appointmentbooking.jwt.service;

import com.perennialsys.appointmentbooking.entityClasses.Admin;
import com.perennialsys.appointmentbooking.entityClasses.Patient;
import com.perennialsys.appointmentbooking.repository.AdminRepository;
import com.perennialsys.appointmentbooking.repository.PatientRepository;
import com.perennialsys.appointmentbooking.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String uId) throws UsernameNotFoundException {

        //Logic to get the user form the Database
        try {
            if (uId.substring(0,3).equalsIgnoreCase("adm"))
            {
                Admin admin =adminRepository.findByuId(uId);
                log.info("admin authentication");
                log.info(admin.getuID()+" "+ admin.getAdminPassword());
                return new User(admin.getuID(), admin.getAdminPassword(),new ArrayList<>());
            }
            else {
                Patient patient = patientRepository.findByuId(uId);
                log.info("patient authentication");
                log.info(patient.getuID()+" "+ patient.getPassword());
                return new User(patient.getuID(), patient.getPassword(), new ArrayList<>());
            }

        }
        catch (UsernameNotFoundException e)
        {
            throw e;
        }
    }

}
