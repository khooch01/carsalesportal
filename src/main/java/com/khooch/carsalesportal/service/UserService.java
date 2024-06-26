package com.khooch.carsalesportal.service;

import com.khooch.carsalesportal.dto.UserRegistrationDto;
import com.khooch.carsalesportal.entity.User;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    User save(User user);
    User save(UserRegistrationDto userDto);
    List<User> findAll();
    User findById(Long id);
    void delete(Long id);
    User update(User user);
    List<User> getAllUsers();
    User getUserById(Long userId);
    void updateUser(User user);
    void deleteUser(Long userId);
    User findUserByUsername(String username);
    User findByUsername(String username);
    User updatedUser(User updatedUser);
    void saveNewUser(User user);
    
}
