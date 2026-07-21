package com.example.footballmanagement.config;

import com.example.footballmanagement.entity.*;
import com.example.footballmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final CoachRepository coachRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final StatisticRepository statisticRepository;
    private final TransferRepository transferRepository;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           TeamRepository teamRepository,
                           CoachRepository coachRepository,
                           PlayerRepository playerRepository,
                           MatchRepository matchRepository,
                           StatisticRepository statisticRepository,
                           TransferRepository transferRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teamRepository = teamRepository;
        this.coachRepository = coachRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.statisticRepository = statisticRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Create Roles if not exist
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN", null, null, new HashSet<>())));
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER", null, null, new HashSet<>())));

        // 2. Create Admin User if not exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("adminpass"));
            adminUser.setRole(adminRole);
            userRepository.save(adminUser);
        }

        // 3. Create Regular User if not exist
        if (userRepository.findByUsername("user").isEmpty()) {
            User regularUser = new User();
            regularUser.setUsername("user");
            regularUser.setEmail("user@example.com");
            regularUser.setPassword(passwordEncoder.encode("userpass"));
            regularUser.setRole(userRole);
            userRepository.save(regularUser);
        }

        // Add another admin user
        if (userRepository.findByUsername("admin2").isEmpty()) {
            User adminUser2 = new User();
            adminUser2.setUsername("admin2");
            adminUser2.setEmail("admin2@example.com");
            adminUser2.setPassword(passwordEncoder.encode("adminpass"));
            adminUser2.setRole(adminRole);
            userRepository.save(adminUser2);
        }

        // Add another regular user
        if (userRepository.findByUsername("user2").isEmpty()) {
            User regularUser2 = new User();
            regularUser2.setUsername("user2");
            regularUser2.setEmail("user2@example.com");
            regularUser2.setPassword(passwordEncoder.encode("userpass"));
            regularUser2.setRole(userRole);
            userRepository.save(regularUser2);
        }

        // Add newuser
        if (userRepository.findByUsername("newuser").isEmpty()) {
            User newUser = new User();
            newUser.setUsername("newuser");
            newUser.setEmail("newuser@example.com");
            newUser.setPassword(passwordEncoder.encode("newuserpass"));
            newUser.setRole(userRole);
            userRepository.save(newUser);
        }


        // 4. Create Coaches if not exist
        Coach ancelotti = coachRepository.findByFirstNameAndLastName("Carlo", "Ancelotti").orElseGet(() -> {
            Coach coach = new Coach(null, "Carlo", "Ancelotti", LocalDate.of(1959, 6, 10), "Italian", null, null, null);
            return coachRepository.save(coach);
        });

        Coach guardiola = coachRepository.findByFirstNameAndLastName("Pep", "Guardiola").orElseGet(() -> {
            Coach coach = new Coach(null, "Pep", "Guardiola", LocalDate.of(1971, 1, 18), "Spanish", null, null, null);
            return coachRepository.save(coach);
        });

        Coach klopp = coachRepository.findByFirstNameAndLastName("Jurgen", "Klopp").orElseGet(() -> {
            Coach coach = new Coach(null, "Jurgen", "Klopp", LocalDate.of(1967, 6, 16), "German", null, null, null);
            return coachRepository.save(coach);
        });

        Coach arteta = coachRepository.findByFirstNameAndLastName("Mikel", "Arteta").orElseGet(() -> {
            Coach coach = new Coach(null, "Mikel", "Arteta", LocalDate.of(1982, 3, 26), "Spanish", null, null, null);
            return coachRepository.save(coach);
        });


        // 5. Create Teams if not exist
        Team realMadrid = teamRepository.findByName("Real Madrid").orElseGet(() -> {
            Team team = new Team(null, "Real Madrid", "Madrid", LocalDate.of(1902, 3, 6), ancelotti, new HashSet<>(), null, null);
            return teamRepository.save(team);
        });
        ancelotti.setTeam(realMadrid);
        coachRepository.save(ancelotti);

        Team manCity = teamRepository.findByName("Manchester City").orElseGet(() -> {
            Team team = new Team(null, "Manchester City", "Manchester", LocalDate.of(1880, 4, 16), guardiola, new HashSet<>(), null, null);
            return teamRepository.save(team);
        });
        guardiola.setTeam(manCity);
        coachRepository.save(guardiola);

        Team liverpool = teamRepository.findByName("Liverpool FC").orElseGet(() -> {
            Team team = new Team(null, "Liverpool FC", "Liverpool", LocalDate.of(1892, 6, 3), klopp, new HashSet<>(), null, null);
            return teamRepository.save(team);
        });
        klopp.setTeam(liverpool);
        coachRepository.save(klopp);

        Team arsenal = teamRepository.findByName("Arsenal FC").orElseGet(() -> {
            Team team = new Team(null, "Arsenal FC", "London", LocalDate.of(1886, 10, 1), arteta, new HashSet<>(), null, null);
            return teamRepository.save(team);
        });
        arteta.setTeam(arsenal);
        coachRepository.save(arteta);


        // 6. Create Players if not exist
        Player benzema = playerRepository.findByFirstNameAndLastName("Karim", "Benzema").orElseGet(() -> {
            Player player = new Player(null, "Karim", "Benzema", LocalDate.of(1987, 12, 19), "French", "Forward", 9, realMadrid, LocalDate.of(2009, 7, 1), LocalDate.of(2023, 6, 30), "Transferred", new HashSet<>(), new HashSet<>(), null, null);
            return playerRepository.save(player);
        });

        Player haaland = playerRepository.findByFirstNameAndLastName("Erling", "Haaland").orElseGet(() -> {
            Player player = new Player(null, "Erling", "Haaland", LocalDate.of(2000, 7, 21), "Norwegian", "Forward", 9, manCity, LocalDate.of(2022, 7, 1), LocalDate.of(2027, 6, 30), "Active", new HashSet<>(), new HashSet<>(), null, null);
            return playerRepository.save(player);
        });

        Player modric = playerRepository.findByFirstNameAndLastName("Luka", "Modric").orElseGet(() -> {
            Player player = new Player(null, "Luka", "Modric", LocalDate.of(1985, 9, 9), "Croatian", "Midfielder", 10, realMadrid, LocalDate.of(2012, 8, 27), LocalDate.of(2024, 6, 30), "Active", new HashSet<>(), new HashSet<>(), null, null);
            return playerRepository.save(player);
        });

        Player salah = playerRepository.findByFirstNameAndLastName("Mohamed", "Salah").orElseGet(() -> {
            Player player = new Player(null, "Mohamed", "Salah", LocalDate.of(1992, 6, 15), "Egyptian", "Forward", 11, liverpool, LocalDate.of(2017, 7, 1), LocalDate.of(2025, 6, 30), "Active", new HashSet<>(), new HashSet<>(), null, null);
            return playerRepository.save(player);
        });

        Player odegaard = playerRepository.findByFirstNameAndLastName("Martin", "Odegaard").orElseGet(() -> {
            Player player = new Player(null, "Martin", "Odegaard", LocalDate.of(1998, 12, 17), "Norwegian", "Midfielder", 8, arsenal, LocalDate.of(2021, 8, 20), LocalDate.of(2028, 6, 30), "Active", new HashSet<>(), new HashSet<>(), null, null);
            return playerRepository.save(player);
        });

        Player deBruyne = playerRepository.findByFirstNameAndLastName("Kevin", "De Bruyne").orElseGet(() -> {
            Player player = new Player(null, "Kevin", "De Bruyne", LocalDate.of(1991, 6, 28), "Belgian", "Midfielder", 17, manCity, LocalDate.of(2015, 8, 30), LocalDate.of(2025, 6, 30), "Active", new HashSet<>(), new HashSet<>(), null, null);
            return playerRepository.save(player);
        });

        // 7. Create Matches if not exist
        Match match1 = matchRepository.findByHomeTeamAndAwayTeamAndMatchDate(realMadrid, manCity, LocalDateTime.of(2024, 8, 15, 20, 0)).orElseGet(() -> {
            Match match = new Match(null, realMadrid, manCity, LocalDateTime.of(2024, 8, 15, 20, 0), "Santiago Bernabeu", 0, 0, "Scheduled", new HashSet<>(), null, null);
            return matchRepository.save(match);
        });

        Match match2 = matchRepository.findByHomeTeamAndAwayTeamAndMatchDate(manCity, realMadrid, LocalDateTime.of(2024, 8, 25, 19, 45)).orElseGet(() -> {
            Match match = new Match(null, manCity, realMadrid, LocalDateTime.of(2024, 8, 25, 19, 45), "Etihad Stadium", 0, 0, "Scheduled", new HashSet<>(), null, null);
            return matchRepository.save(match);
        });

        Match match3 = matchRepository.findByHomeTeamAndAwayTeamAndMatchDate(liverpool, arsenal, LocalDateTime.of(2024, 7, 20, 15, 0)).orElseGet(() -> {
            Match match = new Match(null, liverpool, arsenal, LocalDateTime.of(2024, 7, 20, 15, 0), "Anfield", 2, 1, "Finished", new HashSet<>(), null, null);
            return matchRepository.save(match);
        });

        Match match4 = matchRepository.findByHomeTeamAndAwayTeamAndMatchDate(arsenal, liverpool, LocalDateTime.of(2024, 7, 27, 17, 30)).orElseGet(() -> {
            Match match = new Match(null, arsenal, liverpool, LocalDateTime.of(2024, 7, 27, 17, 30), "Emirates Stadium", 1, 1, "Finished", new HashSet<>(), null, null);
            return matchRepository.save(match);
        });

        Match match5 = matchRepository.findByHomeTeamAndAwayTeamAndMatchDate(realMadrid, liverpool, LocalDateTime.of(2024, 9, 1, 21, 0)).orElseGet(() -> {
            Match match = new Match(null, realMadrid, liverpool, LocalDateTime.of(2024, 9, 1, 21, 0), "Santiago Bernabeu", 0, 0, "Scheduled", new HashSet<>(), null, null);
            return matchRepository.save(match);
        });


        // 8. Create Statistics if not exist
        if (statisticRepository.count() == 0) { // Only add if no statistics exist
            statisticRepository.save(new Statistic(null, benzema, match1, 1, 0, 0, 0, 90, null, null));
            statisticRepository.save(new Statistic(null, haaland, match1, 0, 0, 0, 0, 90, null, null));
            statisticRepository.save(new Statistic(null, salah, match3, 2, 0, 0, 0, 90, null, null));
            statisticRepository.save(new Statistic(null, odegaard, match3, 0, 1, 0, 0, 90, null, null));
            statisticRepository.save(new Statistic(null, odegaard, match4, 1, 0, 0, 0, 90, null, null));
            statisticRepository.save(new Statistic(null, salah, match4, 0, 1, 0, 0, 90, null, null));
            statisticRepository.save(new Statistic(null, deBruyne, match1, 0, 1, 0, 0, 90, null, null));
        }

        // 9. Create Transfers if not exist
        if (transferRepository.count() == 0) { // Only add if no transfers exist
            transferRepository.save(new Transfer(null, benzema, realMadrid, null, LocalDate.of(2023, 7, 1), new BigDecimal("0.00"), null, null)); // Benzema to Al-Ittihad (new team null for simplicity here)
            transferRepository.save(new Transfer(null, haaland, null, manCity, LocalDate.of(2022, 7, 1), new BigDecimal("60000000.00"), null, null)); // Haaland to Man City
            transferRepository.save(new Transfer(null, modric, null, realMadrid, LocalDate.of(2012, 8, 27), new BigDecimal("35000000.00"), null, null)); // Modric to Real Madrid
        }
    }
}