package com.perennialsys.appointmentbooking.classDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private String patientName;
    private String toDiagnose;
    private String password;
}