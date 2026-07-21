package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.ApiResponse;
import com.example.footballmanagement.dto.PlayerDto;
import com.example.footballmanagement.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PlayerDto>>> getAllPlayers() {
        List<PlayerDto> players = playerService.getAllPlayers();
        return ResponseEntity.ok(ApiResponse.success("Players retrieved successfully", players));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlayerDto>> getPlayerById(@PathVariable Long id) {
        PlayerDto player = playerService.getPlayerById(id);
        return ResponseEntity.ok(ApiResponse.success("Player retrieved successfully", player));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<PlayerDto>> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        PlayerDto createdPlayer = playerService.createPlayer(playerDto);
        return new ResponseEntity<>(ApiResponse.success("Player created successfully", createdPlayer), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlayerDto>> updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerDto playerDto) {
        PlayerDto updatedPlayer = playerService.updatePlayer(id, playerDto);
        return ResponseEntity.ok(ApiResponse.success("Player updated successfully", updatedPlayer));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.ok(ApiResponse.success("Player deleted successfully", null));
    }
}
