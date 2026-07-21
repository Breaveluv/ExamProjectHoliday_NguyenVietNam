# Football Club Management System

This project implements a Football Club Management System as described in the Software Requirements Specification (SRS) document. The system allows for managing football teams, players, coaches, matches, statistics, and transfers. It supports two main user roles: ADMIN and USER, with different access levels.

## Functional Requirements (FN01-FN09)

Based on the SRS document, the system provides the following core functionalities:

### FN01 - Authentication (Đăng ký / Đăng nhập / Quên mật khẩu)
*   **Description**: Allows users (ADMIN or USER) to log in to the system using their registered accounts to access functions based on their permissions. Includes registration, login, and password recovery.
*   **Use Case Summary (UC-01 Login)**:
    *   **Actors**: ADMIN, USER
    *   **Pre-condition**: User has a valid account (username, password) and it's active.
    *   **Post-condition**: System issues a JWT access token and redirects the user to the appropriate Dashboard based on their role.
    *   **Basic Flow**: User enters Username/Email and Password, clicks 'Login'. System validates credentials, generates JWT token, and redirects.
    *   **Alternative Flow**: User selects 'Forgot Password' to initiate password recovery.
    *   **Exception Flow**: Invalid credentials or locked account result in error messages.
    *   **Business Rules**: Password must be BCrypt-encoded. JWT token has an expiration time (e.g., 24 hours).

### FN02 - Team Management (Quản lý đội bóng - CRUD)
*   **Description**: ADMIN users can perform Create, Read, Update, and Delete operations on football teams.
*   **Permissions**: ADMIN only.

### FN03 - Player Management (Quản lý cầu thủ - CRUD)
*   **Description**: ADMIN users can perform Create, Read, Update, and Delete operations on coaches.
*   **Permissions**: ADMIN only.

### FN04 - Coach Management (Quản lý huấn luyện viên - CRUD)
*   **Description**: ADMIN users can perform Create, Read, Update, and Delete operations on coaches.
*   **Permissions**: ADMIN only.

### FN05 - Match Management (Quản lý trận đấu - Lịch, Sân, Kết quả)
*   **Description**: ADMIN users can manage match schedules, venues, and update match results.
*   **Permissions**: ADMIN only.

### FN06 - Statistics (Thống kê thi đấu)
*   **Description**: Provides statistics related to matches, such as goals, assists, cards, and playing time.
*   **Permissions**: ADMIN, USER.

### FN07 - Transfer (Chuyển nhượng cầu thủ)
*   **Description**: ADMIN users can manage player transfers (buying and selling players), including transfer history.
*   **Permissions**: ADMIN only.

### FN08 - Dashboard (Dashboard tổng quan)
*   **Description**: Displays an overview of the system, including total teams, total players, upcoming matches, and top scorers.
*   **Permissions**: ADMIN, USER.

### FN09 - Role & User Account Management (Phân quyền & Quản trị tài khoản)
*   **Description**: ADMIN users can manage user roles and user accounts (CRUD operations for roles and users).
*   **Permissions**: ADMIN only.

## Use Case Diagram

The system interacts with two actors: ADMIN and USER.
*   **ADMIN**: Has full control over all functionalities, including authentication, team management, player management, coach management, match management, statistics, transfer management, dashboard, and user/role management.
*   **USER**: Can only perform view/lookup operations for public information such as team lists, player information, match schedules, results, and statistics.

## General System Flow

User accesses the system → Logs in → System authenticates using JWT and authorizes based on role (ADMIN/USER) → Redirects to the corresponding Dashboard → User selects a business function (Team/Player/Coach/Match/Statistics/Transfer Management) → System calls REST API for processing and returns results → Data is displayed on the interface.

## Technology Stack

*   **Backend**: Java 17, Spring Boot 3.x, Gradle, Spring Web, Spring Data JPA, Hibernate, Spring Security + JWT, Validation, Lombok.
*   **Frontend**: Thymeleaf, Bootstrap 5.
*   **Database**: MySQL.

## Database Entities (ERD Summary)

The system includes the following main entities: User, Role, Team, Coach, Player, Match, Statistic, Transfer.
*   A Team has one Coach and many Players.
*   A Player has multiple Statistic records (per Match) and multiple Transfer records.
*   A Match occurs between two Teams (home and away).

## Configuration

The `application.yml` file will be configured for MySQL connection, JWT secret, and server port.

## Testing Instructions (Day 4)

Before testing, ensure your MySQL server is running and the `football_management` database exists. Also, make sure to replace `your_mysql_root_password` and `your_jwt_secret_key_here_please_change_this_in_production` in `src/main/resources/application.properties` with your actual values.

### 1. Register a new User (API)

**Endpoint**: `POST /auth/register`
**Description**: Creates a new user account with the 'USER' role.
**cURL Example**:
```bash
curl -X POST http://localhost:8080/auth/register \
-H "Content-Type: application/json" \
-d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
}'
```
**Expected Response**:
```json
{
    "status": 201,
    "message": "User registered successfully!",
    "data": null,
    "timestamp": "..."
}
```

### 2. Login a User (API)

**Endpoint**: `POST /auth/login-api`
**Description**: Authenticates a user and returns a JWT token.
**cURL Example**:
```bash
curl -X POST http://localhost:8080/auth/login-api \
-H "Content-Type: application/json" \
-d '{
    "usernameOrEmail": "testuser",
    "password": "password123"
}'
```
**Expected Response**:
```json
{
    "status": 200,
    "message": "User logged in successfully!",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "type": "Bearer",
        "id": 1,
        "username": "testuser",
        "email": "test@example.com",
        "role": "USER"
    },
    "timestamp": "..."
}
```
**Note**: Copy the `token` value from the response. You will use it for authenticated requests.

### 3. Access Dashboard Data (API)

**Endpoint**: `GET /api/dashboard`
**Description**: Retrieves general dashboard statistics. This endpoint is publicly accessible for now.
**cURL Example (without token)**:
```bash
curl -X GET http://localhost:8080/api/dashboard
```
**Expected Response**:
```json
{
    "status": 200,
    "message": "Dashboard data retrieved successfully",
    "data": {
        "totalTeams": 0,
        "totalPlayers": 0,
        "upcomingMatches": [],
        "topScorers": []
    },
    "timestamp": "..."
}
```
(Note: `totalTeams`, `totalPlayers`, `upcomingMatches`, `topScorers` will be empty or zero until you add data to the database.)

**cURL Example (with token - optional for this endpoint but good practice for future authenticated endpoints)**:
```bash
curl -X GET http://localhost:8080/api/dashboard \
-H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```
(Replace `YOUR_JWT_TOKEN_HERE` with the token obtained from the login response.)

## Testing Instructions (Day 5)

For testing ADMIN-only functionalities, you will need an ADMIN user.
**Important**: After registering a user (e.g., `adminuser`), you must manually update their role in the database to 'ADMIN' for testing purposes.
Example SQL: `UPDATE users SET role_id = (SELECT id FROM roles WHERE name = 'ADMIN') WHERE username = 'adminuser';`
Then, log in as this `adminuser` to get an ADMIN token.

### 1. Login as ADMIN User (API)

**Endpoint**: `POST /auth/login-api`
**Description**: Authenticates an ADMIN user and returns a JWT token.
**cURL Example**:
```bash
curl -X POST http://localhost:8080/auth/login-api \
-H "Content-Type: application/json" \
-d '{
    "usernameOrEmail": "adminuser",
    "password": "password123"
}'
```
**Expected Response**:
```json
{
    "status": 200,
    "message": "User logged in successfully!",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "type": "Bearer",
        "id": 2, # Assuming this is your admin user ID
        "username": "adminuser",
        "email": "admin@example.com",
        "role": "ADMIN" # This should be ADMIN
    },
    "timestamp": "..."
}
```
**Note**: Copy the `token` value from the response. You will use this ADMIN token for all subsequent ADMIN-only requests.

### 2. Role Management (ADMIN only)

**a. Get All Roles**
**Endpoint**: `GET /api/roles`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/roles \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**b. Get Role by ID (e.g., ID 1 for ADMIN, ID 2 for USER)**
**Endpoint**: `GET /api/roles/{id}`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/roles/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**c. Create a New Role**
**Endpoint**: `POST /api/roles`
**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/roles \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "name": "EDITOR"
}'
```

**d. Update a Role (e.g., update role with ID 3 to "MODERATOR")**
**Endpoint**: `PUT /api/roles/{id}`
**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/roles/3 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "name": "MODERATOR"
}'
```

**e. Delete a Role (e.g., delete role with ID 3)**
**Endpoint**: `DELETE /api/roles/{id}`
**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/roles/3 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

### 3. User Management (ADMIN only)

**a. Get All Users**
**Endpoint**: `GET /api/users`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/users \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**b. Get User by ID (e.g., ID 1 for testuser, ID 2 for adminuser)**
**Endpoint**: `GET /api/users/{id}`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/users/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**c. Create a New User (e.g., with USER role, assuming USER role ID is 2)**
**Endpoint**: `POST /api/users`
**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "newpassword",
    "roleId": 2
}'
```

**d. Update a User (e.g., update user with ID 3, change role to ADMIN if ADMIN role ID is 1)**
**Endpoint**: `PUT /api/users/{id}`
**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/users/3 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "id": 3,
    "username": "updateduser",
    "email": "updateduser@example.com",
    "roleId": 1
}'
```

**e. Delete a User (e.g., delete user with ID 3)**
**Endpoint**: `DELETE /api/users/{id}`
**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/users/3 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

### 4. Team Management

**a. Get All Teams (ADMIN or USER)**
**Endpoint**: `GET /api/teams`
**cURL Example (with ADMIN token)**:
```bash
curl -X GET http://localhost:8080/api/teams \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```
**cURL Example (with USER token)**:
```bash
curl -X GET http://localhost:8080/api/teams \
-H "Authorization: Bearer YOUR_USER_TOKEN_HERE"
```

**b. Get Team by ID (ADMIN or USER)**
**Endpoint**: `GET /api/teams/{id}`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/teams/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**c. Create a New Team (ADMIN only)**
**Endpoint**: `POST /api/teams`
**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/teams \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "name": "Real Madrid",
    "city": "Madrid",
    "foundedDate": "1902-03-06",
    "coachId": null
}'
```
**Note**: `coachId` can be `null` initially. If you want to assign a coach, you would need to create a coach first (not covered in Day 5 scope).

**d. Update a Team (ADMIN only, e.g., update team with ID 1)**
**Endpoint**: `PUT /api/teams/{id}`
**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/teams/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "id": 1,
    "name": "Real Madrid CF",
    "city": "Madrid",
    "foundedDate": "1902-03-06",
    "coachId": null
}'
```

**e. Delete a Team (ADMIN only, e.g., delete team with ID 1)**
**Endpoint**: `DELETE /api/teams/{id}`
**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/teams/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

## Testing Instructions (Day 6)

### 1. Coach Management (ADMIN only)

**a. Create a New Coach**
**Endpoint**: `POST /api/coaches`
**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/coaches \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "firstName": "Carlo",
    "lastName": "Ancelotti",
    "dateOfBirth": "1959-06-10",
    "nationality": "Italian"
}'
```

**b. Get All Coaches**
**Endpoint**: `GET /api/coaches`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/coaches \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**c. Get Coach by ID (e.g., ID 1)**
**Endpoint**: `GET /api/coaches/1`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/coaches/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**d. Update a Coach (e.g., update coach with ID 1)**
**Endpoint**: `PUT /api/coaches/{id}`
**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/coaches/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "firstName": "Carlo",
    "lastName": "Ancelotti",
    "dateOfBirth": "1959-06-10",
    "nationality": "Italiano"
}'
```

**e. Delete a Coach (e.g., delete coach with ID 1)**
**Endpoint**: `DELETE /api/coaches/{id}`
**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/coaches/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

### 2. Player Management

**a. Create a New Player (ADMIN only)**
**Endpoint**: `POST /api/players`
**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/players \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "firstName": "Lionel",
    "lastName": "Messi",
    "dateOfBirth": "1987-06-24",
    "nationality": "Argentinian",
    "position": "Forward",
    "jerseyNumber": 10,
    "teamId": null,
    "contractStartDate": "2023-07-01",
    "contractEndDate": "2025-06-30",
    "status": "Active"
}'
```
**Note**: `teamId` can be `null` initially. If you want to assign a player to a team, ensure the team exists and provide its ID.

**b. Get All Players (ADMIN or USER)**
**Endpoint**: `GET /api/players`
**cURL Example (with ADMIN token)**:
```bash
curl -X GET http://localhost:8080/api/players \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```
**cURL Example (with USER token)**:
```bash
curl -X GET http://localhost:8080/api/players \
-H "Authorization: Bearer YOUR_USER_TOKEN_HERE"
```

**c. Get Player by ID (ADMIN or USER, e.g., ID 1)**
**Endpoint**: `GET /api/players/1`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/players/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**d. Update a Player (ADMIN only, e.g., update player with ID 1)**
**Endpoint**: `PUT /api/players/{id}`
**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/players/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "id": 1,
    "firstName": "Lionel",
    "lastName": "Messi",
    "dateOfBirth": "1987-06-24",
    "nationality": "Argentinian",
    "position": "Forward",
    "jerseyNumber": 10,
    "teamId": 1,
    "contractStartDate": "2023-07-01",
    "contractEndDate": "2025-06-30",
    "status": "Active"
}'
```
**Note**: Replace `teamId: 1` with an existing Team ID if you want to assign the player to a team.

**e. Delete a Player (ADMIN only, e.g., delete player with ID 1)**
**Endpoint**: `DELETE /api/players/{id}`
**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/players/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

## Testing Instructions (Day 7)

### 1. Match Management

**a. Create a New Match (ADMIN only)**
**Endpoint**: `POST /api/matches`
**Description**: Creates a new match between two teams.
**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/matches \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "homeTeamId": 1,
    "awayTeamId": 2,
    "matchDate": "2024-08-15T20:00:00",
    "location": "Santiago Bernabeu",
    "homeScore": 0,
    "awayScore": 0,
    "status": "Scheduled"
}'
```
**Note**: Ensure `homeTeamId` and `awayTeamId` exist and are different.

**b. Get All Matches (ADMIN or USER)**
**Endpoint**: `GET /api/matches`
**cURL Example (with ADMIN token)**:
```bash
curl -X GET http://localhost:8080/api/matches \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**c. Get Match by ID (ADMIN or USER, e.g., ID 1)**
**Endpoint**: `GET /api/matches/1`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/matches/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**d. Update a Match (ADMIN only, e.g., update match with ID 1)**
**Endpoint**: `PUT /api/matches/{id}`
**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/matches/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "id": 1,
    "homeTeamId": 1,
    "awayTeamId": 2,
    "matchDate": "2024-08-15T20:00:00",
    "location": "Santiago Bernabeu",
    "homeScore": 2,
    "awayScore": 1,
    "status": "Finished"
}'
```

**e. Delete a Match (ADMIN only, e.g., delete match with ID 1)**
**Endpoint**: `DELETE /api/matches/{id}`
**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/matches/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

## Testing Instructions (Day 8)

### 1. Statistics Management

**a. Create a New Statistic (ADMIN only)**
**Endpoint**: `POST /api/statistics`
**Description**: Creates a new statistic record for a player in a match.
**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/statistics \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "playerId": 1,
    "matchId": 1,
    "goals": 1,
    "assists": 0,
    "yellowCards": 0,
    "redCards": 0,
    "minutesPlayed": 90
}'
```
**Note**: Ensure `playerId` and `matchId` exist.

**b. Get All Statistics (ADMIN or USER)**
**Endpoint**: `GET /api/statistics`
**cURL Example (with ADMIN token)**:
```bash
curl -X GET http://localhost:8080/api/statistics \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**c. Get Statistic by ID (ADMIN or USER, e.g., ID 1)**
**Endpoint**: `GET /api/statistics/1`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/statistics/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**d. Update a Statistic (ADMIN only, e.g., update statistic with ID 1)**
**Endpoint**: `PUT /api/statistics/{id}`
**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/statistics/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "id": 1,
    "playerId": 1,
    "matchId": 1,
    "goals": 2,
    "assists": 1,
    "yellowCards": 0,
    "redCards": 0,
    "minutesPlayed": 90
}'
```

**e. Delete a Statistic (ADMIN only, e.g., delete statistic with ID 1)**
**Endpoint**: `DELETE /api/statistics/{id}`
**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/statistics/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

## Testing Instructions (Day 9)

### 1. Transfer Management (ADMIN only)

**a. Create a New Transfer**
**Endpoint**: `POST /api/transfers`
**Description**: Creates a new transfer record for a player.
**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/transfers \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "playerId": 1,
    "oldTeamId": 1,
    "newTeamId": 3,
    "transferDate": "2024-07-01",
    "transferFee": 100000000.00
}'
```
**Note**: Ensure `playerId`, `oldTeamId`, and `newTeamId` exist. `oldTeamId` or `newTeamId` can be `null` if the player is a free agent or released.

**b. Get All Transfers**
**Endpoint**: `GET /api/transfers`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/transfers \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**c. Get Transfer by ID (e.g., ID 1)**
**Endpoint**: `GET /api/transfers/1`
**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/transfers/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

**d. Update a Transfer (e.g., update transfer with ID 1)**
**Endpoint**: `PUT /api/transfers/{id}`
**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/transfers/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE" \
-d '{
    "id": 1,
    "playerId": 1,
    "oldTeamId": 1,
    "newTeamId": 4,
    "transferDate": "2024-07-01",
    "transferFee": 120000000.00
}'
```

**e. Delete a Transfer (ADMIN only, e.g., delete transfer with ID 1)**
**Endpoint**: `DELETE /api/transfers/{id}`
**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/transfers/1 \
-H "Authorization: Bearer YOUR_ADMIN_TOKEN_HERE"
```

## Testing Instructions (Day 10 - Thymeleaf Frontend)

### 1. Access Login Page
**URL**: `http://localhost:8080/login`
**Description**: You should see the login form.

### 2. Register a New User (Thymeleaf Form)
**URL**: `http://localhost:8080/register`
**Description**: Fill out the registration form.
**Expected Behavior**:
*   On successful registration, you will be redirected to `/login?success=true`.
*   On failed registration (e.g., duplicate username/email), you will be redirected to `/register?error=message`.

### 3. Login with Registered User (Thymeleaf Form)
**URL**: `http://localhost:8080/login`
**Description**: Use the credentials of a registered user (e.g., `testuser`/`password123` or an ADMIN user you manually created in DB).
**Expected Behavior**:
*   On successful login, you will be redirected to `/dashboard`.
*   On failed login, you will be redirected to `/login?error=true`.

### 4. Access Dashboard Page
**URL**: `http://localhost:8080/dashboard`
**Description**: After successful login, you should see the basic dashboard layout with a navigation sidebar and a welcome message.
**Note**: The dashboard content (Total Teams, Players, etc.) is currently static placeholders. Dynamic data fetching would require JavaScript on the Thymeleaf page or server-side rendering of data.

### 5. Logout
**URL**: `http://localhost:8080/logout` (via POST request, e.g., clicking the logout button on the dashboard)
**Expected Behavior**: You will be redirected to `/login?logout=true`.
