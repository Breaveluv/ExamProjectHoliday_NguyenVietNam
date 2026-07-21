package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.ApiResponse;
import com.example.footballmanagement.dto.TeamDto;
import com.example.footballmanagement.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeamDto>>> getAllTeams() {
        List<TeamDto> teams = teamService.getAllTeams();
        return ResponseEntity.ok(ApiResponse.success("Teams retrieved successfully", teams));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamDto>> getTeamById(@PathVariable Long id) {
        TeamDto team = teamService.getTeamById(id);
        return ResponseEntity.ok(ApiResponse.success("Team retrieved successfully", team));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<TeamDto>> createTeam(@Valid @RequestBody TeamDto teamDto) {
        TeamDto createdTeam = teamService.createTeam(teamDto);
        return new ResponseEntity<>(ApiResponse.success("Team created successfully", createdTeam), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamDto>> updateTeam(@PathVariable Long id, @Valid @RequestBody TeamDto teamDto) {
        TeamDto updatedTeam = teamService.updateTeam(id, teamDto);
        return ResponseEntity.ok(ApiResponse.success("Team updated successfully", updatedTeam));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok(ApiResponse.success("Team deleted successfully", null));
    }
}
