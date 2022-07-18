package com.perennialsys.appointmentbooking.service;


import com.perennialsys.appointmentbooking.entityClasses.Admin;
import com.perennialsys.appointmentbooking.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin save(Admin admin) {
        log.debug("saving admin....!!!");
        return adminRepository.save(admin);
    }
}
