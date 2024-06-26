package com.khooch.carsalesportal.dto;

import java.util.Date;

public class AppointmentDto {
    private Date appointmentDate;
    private Long carId;

    // Getters and Setters
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
