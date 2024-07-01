package com.khooch.carsalesportal.controller.admin;

import com.khooch.carsalesportal.dto.UserDto;
import com.khooch.carsalesportal.entity.Role;
import com.khooch.carsalesportal.entity.User;
import com.khooch.carsalesportal.service.RoleService;
import com.khooch.carsalesportal.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminUsersController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminUsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/viewUsers";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        return "admin/editUser";
    }

    @PostMapping("/admin/users/save")
    public String saveUser(@ModelAttribute("user") User user) {
        // Validate input if necessary
        if (user.getId() == null) {
            // New user, handle password setting securely
            userService.saveNewUser(user);
        } else {
            // Existing user, ensure to keep existing password if not updated
            User existingUser = userService.findById(user.getId());
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword()); // Keep existing password
            }

            // Add role_admin if not already present
            Role roleAdmin = roleService.findByName("ROLE_ADMIN");
            if (!user.getRoles().contains(roleAdmin)) {
                user.getRoles().add(roleAdmin);
            }

            userService.updateUser(user);
        }
        return "redirect:/admin/users";
    }
    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/profile/update")
    public String updateUserProfileForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        // Do not set password here as we don't want to display it in the form
        model.addAttribute("userDto", userDto);
        return "admin/profile";
    }

    @PostMapping("/admin/profile/update")
    public String updateUserProfile(@ModelAttribute("userDto") UserDto userDto) {
        User user = userService.findById(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            // Update password only if a new password is provided
            user.setPassword(userDto.getPassword());
        }
        userService.save(user);
        return "redirect:/admin/home";
    }
}
