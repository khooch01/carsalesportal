package com.khooch.carsalesportal;

import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.repository.UserRepository;
import com.khooch.carsalesportal.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserRegistration_WeakPassword() {
        // Arrange
        String username = "newUser";
        String weakPassword = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(weakPassword);

        // Act
        boolean result = userService.registerUser(user);

        // Assert
        assertFalse(result, "Registration should fail with a weak password.");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testUserRegistration_ExistingUsername() {
        // Arrange
        String username = "existingUser";
        String strongPassword = "StrongP@ssw0rd";
        User existingUser = new User();
        existingUser.setUsername(username);
        existingUser.setPassword(strongPassword);

        when(userRepository.findByUsername(username)).thenReturn(existingUser);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(strongPassword);

        // Act
        boolean result = userService.registerUser(newUser);

        // Assert
        assertFalse(result, "Registration should fail for an existing username.");
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    public void testUserRegistration_Successful() {
        // Arrange
        String username = "newUser";
        String strongPassword = "StrongP@ssw0rd";
        User user = new User();
        user.setUsername(username);
        user.setPassword(strongPassword);

        when(passwordEncoder.encode(strongPassword)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        boolean result = userService.registerUser(user);

        // Assert
        assertTrue(result, "Registration should succeed with valid username and strong password.");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUserLogin_IncorrectCredentials() {
        // Arrange
        String username = "user1";
        String wrongPassword = "wrongPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedCorrectPassword"); // Assume this is the correct encoded password

        when(userRepository.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.matches(wrongPassword, user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            userService.login(username, wrongPassword);
        });
    }
    
}
