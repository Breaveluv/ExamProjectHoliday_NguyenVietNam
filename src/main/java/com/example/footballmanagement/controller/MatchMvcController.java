package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.MatchDto;
import com.example.footballmanagement.service.MatchService;
import com.example.footballmanagement.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/matches") // Changed to /matches for Thymeleaf views
public class MatchMvcController {

    private final MatchService matchService;
    private final TeamService teamService; // To get list of teams for dropdown

    public MatchMvcController(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public String listMatches(Model model) {
        List<MatchDto> matches = matchService.getAllMatches();
        model.addAttribute("matches", matches);
        return "matches/list"; // Return matches/list.html
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("match", new MatchDto());
        model.addAttribute("teams", teamService.getAllTeams()); // Provide teams for dropdown
        return "matches/form"; // Return matches/form.html for creation
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public String createMatch(@Valid @ModelAttribute("match") MatchDto matchDto,
                              BindingResult result,
                              Model model, // Added Model here
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred."); // Use model directly
            model.addAttribute("teams", teamService.getAllTeams()); // Re-add teams for dropdown
            return "matches/form"; // Stay on the form to show errors
        }
        try {
            matchService.createMatch(matchDto);
            redirectAttributes.addFlashAttribute("successMessage", "Match created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // If redirecting, model attributes are lost, so use RedirectAttributes for error messages
            return "redirect:/matches/new";
        }
        return "redirect:/matches";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MatchDto match = matchService.getMatchById(id);
        model.addAttribute("match", match);
        model.addAttribute("teams", teamService.getAllTeams()); // Provide teams for dropdown
        return "matches/form"; // Return matches/form.html for editing
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String updateMatch(@PathVariable Long id,
                              @Valid @ModelAttribute("match") MatchDto matchDto,
                              BindingResult result,
                              Model model, // Added Model here
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred."); // Use model directly
            model.addAttribute("teams", teamService.getAllTeams()); // Re-add teams for dropdown
            return "matches/form"; // Stay on the form to show errors
        }
        try {
            matchService.updateMatch(id, matchDto);
            redirectAttributes.addFlashAttribute("successMessage", "Match updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // If redirecting, model attributes are lost, so use RedirectAttributes for error messages
            return "redirect:/matches/edit/" + id;
        }
        return "redirect:/matches";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteMatch(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            matchService.deleteMatch(id);
            redirectAttributes.addFlashAttribute("successMessage", "Match deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/matches";
    }
}
