package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.TransferDto;
import com.example.footballmanagement.service.PlayerService;
import com.example.footballmanagement.service.TeamService;
import com.example.footballmanagement.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/transfers") // Changed to /transfers for Thymeleaf views
@PreAuthorize("hasRole('ADMIN')") // All transfer management pages require ADMIN role
public class TransferMvcController {

    private final TransferService transferService;
    private final PlayerService playerService; // To get list of players for dropdown
    private final TeamService teamService;     // To get list of teams for dropdown

    public TransferMvcController(TransferService transferService, PlayerService playerService, TeamService teamService) {
        this.transferService = transferService;
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping
    public String listTransfers(Model model) {
        List<TransferDto> transfers = transferService.getAllTransfers();
        model.addAttribute("transfers", transfers);
        return "transfers/list"; // Return transfers/list.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("transfer", new TransferDto());
        model.addAttribute("players", playerService.getAllPlayers()); // Provide players for dropdown
        model.addAttribute("teams", teamService.getAllTeams());     // Provide teams for dropdown
        return "transfers/form"; // Return transfers/form.html for creation
    }

    @PostMapping("/new")
    public String createTransfer(@Valid @ModelAttribute("transfer") TransferDto transferDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            model.addAttribute("players", playerService.getAllPlayers()); // Re-add players for dropdown
            model.addAttribute("teams", teamService.getAllTeams());     // Re-add teams for dropdown
            return "transfers/form"; // Stay on the form to show errors
        }
        try {
            transferService.createTransfer(transferDto);
            redirectAttributes.addFlashAttribute("successMessage", "Transfer created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/transfers/new";
        }
        return "redirect:/transfers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        TransferDto transfer = transferService.getTransferById(id);
        model.addAttribute("transfer", transfer);
        model.addAttribute("players", playerService.getAllPlayers()); // Provide players for dropdown
        model.addAttribute("teams", teamService.getAllTeams());     // Provide teams for dropdown
        return "transfers/form"; // Return transfers/form.html for editing
    }

    @PostMapping("/edit/{id}")
    public String updateTransfer(@PathVariable Long id,
                                 @Valid @ModelAttribute("transfer") TransferDto transferDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Validation errors occurred.");
            model.addAttribute("players", playerService.getAllPlayers()); // Re-add players for dropdown
            model.addAttribute("teams", teamService.getAllTeams());     // Re-add teams for dropdown
            return "transfers/form"; // Stay on the form to show errors
        }
        try {
            transferService.updateTransfer(id, transferDto);
            redirectAttributes.addFlashAttribute("successMessage", "Transfer updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/transfers/edit/" + id;
        }
        return "redirect:/transfers";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransfer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            transferService.deleteTransfer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Transfer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/transfers";
    }
}
