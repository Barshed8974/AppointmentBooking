package com.perennialsys.appointmentbooking.classDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckAvailabilityDTO {
    private Long doctorId;
    private LocalDate localDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
