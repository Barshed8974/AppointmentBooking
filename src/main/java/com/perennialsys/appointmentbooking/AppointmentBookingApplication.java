package com.perennialsys.appointmentbooking;

import com.perennialsys.appointmentbooking.controller.AdminController;
import com.perennialsys.appointmentbooking.entityClasses.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppointmentBookingApplication  {

    public static void main(String[] args) {

        SpringApplication.run(AppointmentBookingApplication.class, args);
        System.out.println("starting");
    }

}
