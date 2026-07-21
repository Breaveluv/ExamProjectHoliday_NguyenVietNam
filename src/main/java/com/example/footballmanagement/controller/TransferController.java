package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.ApiResponse;
import com.example.footballmanagement.dto.TransferDto;
import com.example.footballmanagement.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TransferDto>>> getAllTransfers() {
        List<TransferDto> transfers = transferService.getAllTransfers();
        return ResponseEntity.ok(ApiResponse.success("Transfers retrieved successfully", transfers));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransferDto>> getTransferById(@PathVariable Long id) {
        TransferDto transfer = transferService.getTransferById(id);
        return ResponseEntity.ok(ApiResponse.success("Transfer retrieved successfully", transfer));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<TransferDto>> createTransfer(@Valid @RequestBody TransferDto transferDto) {
        TransferDto createdTransfer = transferService.createTransfer(transferDto);
        return new ResponseEntity<>(ApiResponse.success("Transfer created successfully", createdTransfer), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TransferDto>> updateTransfer(@PathVariable Long id, @Valid @RequestBody TransferDto transferDto) {
        TransferDto updatedTransfer = transferService.updateTransfer(id, transferDto);
        return ResponseEntity.ok(ApiResponse.success("Transfer updated successfully", updatedTransfer));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransfer(@PathVariable Long id) {
        transferService.deleteTransfer(id);
        return ResponseEntity.ok(ApiResponse.success("Transfer deleted successfully", null));
    }
}
