package com.perennialsys.appointmentbooking.classDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorsScheduleDTO {
    private Long doctorId;
    private DayOfWeek dayOfWeek;
    private LocalTime entryTime;
    private LocalTime exitTime;
}
