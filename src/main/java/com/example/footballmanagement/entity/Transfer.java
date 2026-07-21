package com.example.footballmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"player", "oldTeam", "newTeam"}) // Exclude related entities to prevent StackOverflowError
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @NotNull(message = "Player cannot be empty")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_team_id")
    private Team oldTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_team_id")
    @NotNull(message = "New team cannot be empty")
    private Team newTeam;

    @Column(name = "transfer_date", nullable = false)
    @NotNull(message = "Transfer date cannot be empty")
    private LocalDate transferDate;

    @Column(name = "transfer_fee", precision = 19, scale = 2)
    private BigDecimal transferFee;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
