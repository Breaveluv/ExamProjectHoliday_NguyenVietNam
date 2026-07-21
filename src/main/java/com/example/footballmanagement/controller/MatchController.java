package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.ApiResponse;
import com.example.footballmanagement.dto.MatchDto;
import com.example.footballmanagement.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MatchDto>>> getAllMatches() {
        List<MatchDto> matches = matchService.getAllMatches();
        return ResponseEntity.ok(ApiResponse.success("Matches retrieved successfully", matches));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MatchDto>> getMatchById(@PathVariable Long id) {
        MatchDto match = matchService.getMatchById(id);
        return ResponseEntity.ok(ApiResponse.success("Match retrieved successfully", match));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<MatchDto>> createMatch(@Valid @RequestBody MatchDto matchDto) {
        MatchDto createdMatch = matchService.createMatch(matchDto);
        return new ResponseEntity<>(ApiResponse.success("Match created successfully", createdMatch), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MatchDto>> updateMatch(@PathVariable Long id, @Valid @RequestBody MatchDto matchDto) {
        MatchDto updatedMatch = matchService.updateMatch(id, matchDto);
        return ResponseEntity.ok(ApiResponse.success("Match updated successfully", updatedMatch));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.ok(ApiResponse.success("Match deleted successfully", null));
    }
}
