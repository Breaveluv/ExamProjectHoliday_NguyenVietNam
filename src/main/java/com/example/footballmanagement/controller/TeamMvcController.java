package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.CoachDto;
import com.example.footballmanagement.dto.TeamDto;
import com.example.footballmanagement.service.CoachService;
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
@RequestMapping("/teams") // Changed to /teams for Thymeleaf views
public class TeamMvcController {

    private final TeamService teamService;
    private final CoachService coachService; // To get list of coaches for dropdown

    public TeamMvcController(TeamService teamService, CoachService coachService) {
        this.teamService = teamService;
        this.coachService = coachService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public String listTeams(Model model) {
        List<TeamDto> teams = teamService.getAllTeams();
        model.addAttribute("teams", teams);
        return "teams/list"; // Return teams/list.html
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("team", new TeamDto());
        model.addAttribute("coaches", coachService.getAllCoaches()); // Provide coaches for dropdown
        return "teams/form"; // Return teams/form.html for creation
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public String createTeam(@Valid @ModelAttribute("team") TeamDto teamDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            model.addAttribute("coaches", coachService.getAllCoaches());
            return "teams/form"; // Stay on the form to show errors
        }
        try {
            teamService.createTeam(teamDto);
            redirectAttributes.addFlashAttribute("successMessage", "Team created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/teams/new";
        }
        return "redirect:/teams";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        TeamDto team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        model.addAttribute("coaches", coachService.getAllCoaches()); // Provide coaches for dropdown
        return "teams/form"; // Return teams/form.html for editing
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String updateTeam(@PathVariable Long id,
                             @Valid @ModelAttribute("team") TeamDto teamDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            model.addAttribute("coaches", coachService.getAllCoaches());
            return "teams/form"; // Stay on the form to show errors
        }
        try {
            teamService.updateTeam(id, teamDto);
            redirectAttributes.addFlashAttribute("successMessage", "Team updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/teams/edit/" + id;
        }
        return "redirect:/teams";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            teamService.deleteTeam(id);
            redirectAttributes.addFlashAttribute("successMessage", "Team deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/teams";
    }
}
