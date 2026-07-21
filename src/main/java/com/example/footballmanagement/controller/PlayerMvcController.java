package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.PlayerDto;
import com.example.footballmanagement.dto.TeamDto;
import com.example.footballmanagement.service.PlayerService;
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
@RequestMapping("/players") // Changed to /players for Thymeleaf views
public class PlayerMvcController {

    private final PlayerService playerService;
    private final TeamService teamService; // To get list of teams for dropdown

    public PlayerMvcController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public String listPlayers(Model model) {
        List<PlayerDto> players = playerService.getAllPlayers();
        model.addAttribute("players", players);
        return "players/list"; // Return players/list.html
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("player", new PlayerDto());
        model.addAttribute("teams", teamService.getAllTeams()); // Provide teams for dropdown
        return "players/form"; // Return players/form.html for creation
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public String createPlayer(@Valid @ModelAttribute("player") PlayerDto playerDto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Re-add teams dropdown data so the form can render correctly
            model.addAttribute("teams", teamService.getAllTeams());
            return "players/form"; // Stay on the form to show errors
        }
        try {
            playerService.createPlayer(playerDto);
            redirectAttributes.addFlashAttribute("successMessage", "Player created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/players/new";
        }
        return "redirect:/players";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        PlayerDto player = playerService.getPlayerById(id);
        model.addAttribute("player", player);
        model.addAttribute("teams", teamService.getAllTeams()); // Provide teams for dropdown
        return "players/form"; // Return players/form.html for editing
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String updatePlayer(@PathVariable Long id,
                               @Valid @ModelAttribute("player") PlayerDto playerDto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Re-add teams dropdown data so the form can render correctly
            model.addAttribute("teams", teamService.getAllTeams());
            return "players/form"; // Stay on the form to show errors
        }
        try {
            playerService.updatePlayer(id, playerDto);
            redirectAttributes.addFlashAttribute("successMessage", "Player updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/players/edit/" + id;
        }
        return "redirect:/players";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            playerService.deletePlayer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Player deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/players";
    }
}
