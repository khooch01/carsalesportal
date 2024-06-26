package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
}
