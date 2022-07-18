package com.perennialsys.appointmentbooking.classDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookAppointmentDTO {
    private Long doctorId;
    private Long patientId;
    private LocalDate localDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH-mm-ss")
    private LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH-mm-ss")
    private LocalTime endTime;
}
