package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.CreateUserRequest;
import com.example.footballmanagement.dto.RoleDto;
import com.example.footballmanagement.dto.UserDto;
import com.example.footballmanagement.service.RoleService;
import com.example.footballmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users") // Changed to /users for Thymeleaf views
@PreAuthorize("hasRole('ADMIN')") // All user management pages require ADMIN role
public class UserMvcController {

    private final UserService userService;
    private final RoleService roleService;

    public UserMvcController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list"; // Return users/list.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new CreateUserRequest()); // Use CreateUserRequest for new user
        model.addAttribute("roles", roleService.getAllRoles());
        return "users/form"; // Return users/form.html for creation
    }

    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute("user") CreateUserRequest createUserRequest,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            model.addAttribute("roles", roleService.getAllRoles());
            return "users/form"; // Stay on the form to show errors
        }
        try {
            userService.createUser(
                    new UserDto(null, createUserRequest.getUsername(), createUserRequest.getEmail(), createUserRequest.getRoleId(), null),
                    createUserRequest.getPassword()
            );
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/new";
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDto user = userService.getUserById(id);
        model.addAttribute("user", user); // Use UserDto for editing
        model.addAttribute("roles", roleService.getAllRoles());
        return "users/form"; // Return users/form.html for editing
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("user") UserDto userDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            model.addAttribute("roles", roleService.getAllRoles());
            return "users/form"; // Stay on the form to show errors
        }
        try {
            userService.updateUser(id, userDto);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/edit/" + id;
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }
}
