package com.perennialsys.appointmentbooking.classDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorDTO {
    private String doctorName;
    private String doctorSpeciality;
    private String area;
    private Long phoneNumber;
}
