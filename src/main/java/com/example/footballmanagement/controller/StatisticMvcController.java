package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.StatisticDto;
import com.example.footballmanagement.service.MatchService;
import com.example.footballmanagement.service.PlayerService;
import com.example.footballmanagement.service.StatisticService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/statistics") // Changed to /statistics for Thymeleaf views
public class StatisticMvcController {

    private final StatisticService statisticService;
    private final PlayerService playerService; // To get list of players for dropdown
    private final MatchService matchService;   // To get list of matches for dropdown

    public StatisticMvcController(StatisticService statisticService, PlayerService playerService, MatchService matchService) {
        this.statisticService = statisticService;
        this.playerService = playerService;
        this.matchService = matchService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public String listStatistics(Model model) {
        List<StatisticDto> statistics = statisticService.getAllStatistics();
        model.addAttribute("statistics", statistics);
        return "statistics/list"; // Return statistics/list.html
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("statistic", new StatisticDto());
        model.addAttribute("players", playerService.getAllPlayers()); // Provide players for dropdown
        model.addAttribute("matches", matchService.getAllMatches());   // Provide matches for dropdown
        return "statistics/form"; // Return statistics/form.html for creation
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public String createStatistic(@Valid @ModelAttribute("statistic") StatisticDto statisticDto,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            model.addAttribute("players", playerService.getAllPlayers()); // Re-add players for dropdown
            model.addAttribute("matches", matchService.getAllMatches());   // Re-add matches for dropdown
            return "statistics/form"; // Stay on the form to show errors
        }
        try {
            statisticService.createStatistic(statisticDto);
            redirectAttributes.addFlashAttribute("successMessage", "Statistic created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/statistics/new";
        }
        return "redirect:/statistics";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        StatisticDto statistic = statisticService.getStatisticById(id);
        model.addAttribute("statistic", statistic);
        model.addAttribute("players", playerService.getAllPlayers()); // Provide players for dropdown
        model.addAttribute("matches", matchService.getAllMatches());   // Provide matches for dropdown
        return "statistics/form"; // Return statistics/form.html for editing
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String updateStatistic(@PathVariable Long id,
                                  @Valid @ModelAttribute("statistic") StatisticDto statisticDto,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            model.addAttribute("players", playerService.getAllPlayers()); // Re-add players for dropdown
            model.addAttribute("matches", matchService.getAllMatches());   // Re-add matches for dropdown
            return "statistics/form"; // Stay on the form to show errors
        }
        try {
            statisticService.updateStatistic(id, statisticDto);
            redirectAttributes.addFlashAttribute("successMessage", "Statistic updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/statistics/edit/" + id;
        }
        return "redirect:/statistics";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteStatistic(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            statisticService.deleteStatistic(id);
            redirectAttributes.addFlashAttribute("successMessage", "Statistic deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/statistics";
    }
}
