package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.RoleDto;
import com.example.footballmanagement.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/roles") // Changed to /roles for Thymeleaf views
@PreAuthorize("hasRole('ADMIN')") // All role management pages require ADMIN role
public class RoleMvcController {

    private final RoleService roleService;

    public RoleMvcController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String listRoles(Model model) {
        List<RoleDto> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "roles/list"; // Return roles/list.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("role", new RoleDto());
        return "roles/form"; // Return roles/form.html for creation
    }

    @PostMapping("/new")
    public String createRole(@Valid @ModelAttribute("role") RoleDto roleDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            return "roles/form";
        }
        try {
            roleService.createRole(roleDto);
            redirectAttributes.addFlashAttribute("successMessage", "Role created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/roles/new";
        }
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        RoleDto role = roleService.getRoleById(id);
        model.addAttribute("role", role);
        return "roles/form"; // Return roles/form.html for editing
    }

    @PostMapping("/edit/{id}")
    public String updateRole(@PathVariable Long id,
                             @Valid @ModelAttribute("role") RoleDto roleDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            return "roles/form";
        }
        try {
            roleService.updateRole(id, roleDto);
            redirectAttributes.addFlashAttribute("successMessage", "Role updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/roles/edit/" + id;
        }
        return "redirect:/roles";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            roleService.deleteRole(id);
            redirectAttributes.addFlashAttribute("successMessage", "Role deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/roles";
    }
}
