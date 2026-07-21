package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.TransferDto;
import com.example.footballmanagement.entity.Player;
import com.example.footballmanagement.entity.Team;
import com.example.footballmanagement.entity.Transfer;
import com.example.footballmanagement.repository.PlayerRepository;
import com.example.footballmanagement.repository.TeamRepository;
import com.example.footballmanagement.repository.TransferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public TransferService(TransferRepository transferRepository, PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.transferRepository = transferRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<TransferDto> getAllTransfers() {
        return transferRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TransferDto getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found with id: " + id));
        return mapToDto(transfer);
    }

    public TransferDto createTransfer(TransferDto transferDto) {
        Player player = playerRepository.findById(transferDto.getPlayerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + transferDto.getPlayerId()));

        Team oldTeam = null;
        if (transferDto.getOldTeamId() != null) {
            oldTeam = teamRepository.findById(transferDto.getOldTeamId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Old team not found with id: " + transferDto.getOldTeamId()));
        }

        Team newTeam = null;
        if (transferDto.getNewTeamId() != null) {
            newTeam = teamRepository.findById(transferDto.getNewTeamId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "New team not found with id: " + transferDto.getNewTeamId()));
        }

        if (oldTeam != null && newTeam != null && oldTeam.equals(newTeam)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old team and new team cannot be the same for a transfer.");
        }

        Transfer transfer = new Transfer();
        transfer.setPlayer(player);
        transfer.setOldTeam(oldTeam);
        transfer.setNewTeam(newTeam);
        transfer.setTransferDate(transferDto.getTransferDate());
        transfer.setTransferFee(transferDto.getTransferFee());

        // Update player's team and status if newTeam is provided
        if (newTeam != null) {
            player.setTeam(newTeam);
            player.setStatus("Transferred"); // Or another appropriate status
            playerRepository.save(player);
        } else if (oldTeam != null && newTeam == null) {
            // If player is transferred out of a team without a new team (e.g., free agent)
            player.setTeam(null);
            player.setStatus("Free Agent"); // Example status
            playerRepository.save(player);
        }


        Transfer savedTransfer = transferRepository.save(transfer);
        return mapToDto(savedTransfer);
    }

    public TransferDto updateTransfer(Long id, TransferDto transferDto) {
        Transfer existingTransfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found with id: " + id));

        Player player = playerRepository.findById(transferDto.getPlayerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + transferDto.getPlayerId()));

        Team oldTeam = null;
        if (transferDto.getOldTeamId() != null) {
            oldTeam = teamRepository.findById(transferDto.getOldTeamId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Old team not found with id: " + transferDto.getOldTeamId()));
        }

        Team newTeam = null;
        if (transferDto.getNewTeamId() != null) {
            newTeam = teamRepository.findById(transferDto.getNewTeamId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "New team not found with id: " + transferDto.getNewTeamId()));
        }

        if (oldTeam != null && newTeam != null && oldTeam.equals(newTeam)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old team and new team cannot be the same for a transfer.");
        }

        existingTransfer.setPlayer(player);
        existingTransfer.setOldTeam(oldTeam);
        existingTransfer.setNewTeam(newTeam);
        existingTransfer.setTransferDate(transferDto.getTransferDate());
        existingTransfer.setTransferFee(transferDto.getTransferFee());

        // Update player's team and status if newTeam is provided
        if (newTeam != null) {
            player.setTeam(newTeam);
            player.setStatus("Transferred");
            playerRepository.save(player);
        } else if (oldTeam != null && newTeam == null) {
            player.setTeam(null);
            player.setStatus("Free Agent");
            playerRepository.save(player);
        } else if (oldTeam == null && newTeam == null && player.getTeam() != null) {
            // If both old and new teams are null, but player still has a team, disassociate
            player.setTeam(null);
            player.setStatus("Free Agent");
            playerRepository.save(player);
        }


        Transfer updatedTransfer = transferRepository.save(existingTransfer);
        return mapToDto(updatedTransfer);
    }

    public void deleteTransfer(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found with id: " + id));
        transferRepository.delete(transfer);
    }

    private TransferDto mapToDto(Transfer transfer) {
        String playerName = transfer.getPlayer().getFirstName() + " " + transfer.getPlayer().getLastName();
        String oldTeamName = Optional.ofNullable(transfer.getOldTeam()).map(Team::getName).orElse("N/A");
        String newTeamName = Optional.ofNullable(transfer.getNewTeam()).map(Team::getName).orElse("N/A");

        return new TransferDto(
                transfer.getId(),
                transfer.getPlayer().getId(),
                playerName,
                Optional.ofNullable(transfer.getOldTeam()).map(Team::getId).orElse(null),
                oldTeamName,
                Optional.ofNullable(transfer.getNewTeam()).map(Team::getId).orElse(null),
                newTeamName,
                transfer.getTransferDate(),
                transfer.getTransferFee()
        );
    }
}
