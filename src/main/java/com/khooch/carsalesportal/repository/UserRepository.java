package com.khooch.carsalesportal.repository;

import com.khooch.carsalesportal.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
