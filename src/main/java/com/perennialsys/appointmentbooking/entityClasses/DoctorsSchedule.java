package com.perennialsys.appointmentbooking.entityClasses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorsSchedule {

    @Id
    @GeneratedValue
    private Long id;
    private Long doctorId;
    private DayOfWeek dayOfWeek;
    private LocalTime entryTime;
    private LocalTime exitTime;
}
