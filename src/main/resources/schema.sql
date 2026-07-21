-- Drop tables if they exist to allow for clean re-creation during development
DROP TABLE IF EXISTS transfers;
DROP TABLE IF EXISTS statistics;
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS coaches;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

-- Table: roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- e.g., ADMIN, USER
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table: users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Table: teams
CREATE TABLE teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    city VARCHAR(100),
    founded_date DATE,
    coach_id BIGINT UNIQUE, -- One-to-One relationship with Coach
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table: coaches
CREATE TABLE coaches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    nationality VARCHAR(50),
    team_id BIGINT UNIQUE, -- One-to-One relationship with Team
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE SET NULL
);

-- Add foreign key constraint for teams.coach_id after coaches table is created
ALTER TABLE teams
ADD CONSTRAINT fk_team_coach
FOREIGN KEY (coach_id) REFERENCES coaches(id) ON DELETE SET NULL;


-- Table: players
CREATE TABLE players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    nationality VARCHAR(50),
    position VARCHAR(50), -- e.g., Goalkeeper, Defender, Midfielder, Forward
    jersey_number INT,
    team_id BIGINT,
    contract_start_date DATE,
    contract_end_date DATE,
    status VARCHAR(50) NOT NULL, -- e.g., New, Active, Transferred, Retired
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE SET NULL
);

-- Table: matches
CREATE TABLE matches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    home_team_id BIGINT NOT NULL,
    away_team_id BIGINT NOT NULL,
    match_date DATETIME NOT NULL,
    location VARCHAR(255),
    home_score INT DEFAULT 0,
    away_score INT DEFAULT 0,
    status VARCHAR(50) NOT NULL, -- e.g., Scheduled, Ongoing, Finished, Postponed, Cancelled
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (home_team_id) REFERENCES teams(id),
    FOREIGN KEY (away_team_id) REFERENCES teams(id),
    CONSTRAINT chk_different_teams CHECK (home_team_id <> away_team_id)
);

-- Table: statistics
CREATE TABLE statistics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    match_id BIGINT NOT NULL,
    goals INT DEFAULT 0,
    assists INT DEFAULT 0,
    yellow_cards INT DEFAULT 0,
    red_cards INT DEFAULT 0,
    minutes_played INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES matches(id) ON DELETE CASCADE,
    UNIQUE (player_id, match_id) -- A player can only have one statistic record per match
);

-- Table: transfers
CREATE TABLE transfers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    old_team_id BIGINT,
    new_team_id BIGINT,
    transfer_date DATE NOT NULL,
    transfer_fee DECIMAL(19, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES players(id) ON DELETE CASCADE,
    FOREIGN KEY (old_team_id) REFERENCES teams(id) ON DELETE SET NULL,
    FOREIGN KEY (new_team_id) REFERENCES teams(id) ON DELETE SET NULL,
    CONSTRAINT chk_different_transfer_teams CHECK (old_team_id <> new_team_id OR (old_team_id IS NULL AND new_team_id IS NOT NULL) OR (old_team_id IS NOT NULL AND new_team_id IS NULL))
);

-- Initial data for roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');
