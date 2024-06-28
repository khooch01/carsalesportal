package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.Appointment;
import com.khooch.carsalesportal.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUserAndStatus(User user, String status);
}
