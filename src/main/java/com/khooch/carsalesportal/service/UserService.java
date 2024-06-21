package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.dto.UserRegistrationDto;
import com.khooch.carsalesportal.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    User findByUsername(String username);
    void deleteById(Long id);
    List<User> findAll();
}
