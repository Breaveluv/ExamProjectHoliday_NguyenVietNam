package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.CoachDto;
import com.example.footballmanagement.service.CoachService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/coaches") // Changed to /coaches for Thymeleaf views
@PreAuthorize("hasRole('ADMIN')") // All coach management pages require ADMIN role
public class CoachMvcController {

    private final CoachService coachService;

    public CoachMvcController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping
    public String listCoaches(Model model) {
        List<CoachDto> coaches = coachService.getAllCoaches();
        model.addAttribute("coaches", coaches);
        return "coaches/list"; // Return coaches/list.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("coach", new CoachDto());
        return "coaches/form"; // Return coaches/form.html for creation
    }

    @PostMapping("/new")
    public String createCoach(@Valid @ModelAttribute("coach") CoachDto coachDto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            return "coaches/form"; // Stay on the form to show errors
        }
        try {
            coachService.createCoach(coachDto);
            redirectAttributes.addFlashAttribute("successMessage", "Coach created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/coaches/new";
        }
        return "redirect:/coaches";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        CoachDto coach = coachService.getCoachById(id);
        model.addAttribute("coach", coach);
        return "coaches/form"; // Return coaches/form.html for editing
    }

    @PostMapping("/edit/{id}")
    public String updateCoach(@PathVariable Long id,
                              @Valid @ModelAttribute("coach") CoachDto coachDto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            return "coaches/form"; // Stay on the form to show errors
        }
        try {
            coachService.updateCoach(id, coachDto);
            redirectAttributes.addFlashAttribute("successMessage", "Coach updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/coaches/edit/" + id;
        }
        return "redirect:/coaches";
    }

    @GetMapping("/delete/{id}")
    public String deleteCoach(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            coachService.deleteCoach(id);
            redirectAttributes.addFlashAttribute("successMessage", "Coach deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/coaches";
    }
}
