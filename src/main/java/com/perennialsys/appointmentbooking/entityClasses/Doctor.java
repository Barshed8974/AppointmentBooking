package com.perennialsys.appointmentbooking.entityClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Doctor {
    @Id
    @GeneratedValue
    private long doctorId;
    private String doctorName;
    private String doctorSpeciality;
    private String area;
    private Long phoneNumber;
}
