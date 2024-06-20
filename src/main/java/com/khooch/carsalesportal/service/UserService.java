package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    User findByUsername(String username);
    void updateUser(User user, String username);
    List<User> getAllUsers();
    void makeAdmin(Long id);
}
