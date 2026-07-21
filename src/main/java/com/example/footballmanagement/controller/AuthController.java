package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.ApiResponse;
import com.example.footballmanagement.dto.auth.JwtResponse;
import com.example.footballmanagement.dto.auth.LoginRequest;
import com.example.footballmanagement.dto.auth.RegisterRequest;
import com.example.footballmanagement.entity.Role;
import com.example.footballmanagement.entity.User;
import com.example.footballmanagement.repository.RoleRepository;
import com.example.footballmanagement.repository.UserRepository;
import com.example.footballmanagement.security.JwtTokenProvider;
import com.example.footballmanagement.security.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller // Changed to @Controller for Thymeleaf views
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider,
                          UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    // This method is for API-based login, not directly used by Thymeleaf form login
    @PostMapping("/login-api") // Renamed to avoid conflict with Spring Security's /login
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail());
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseGet(() -> userRepository.findByEmail(userDetails.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found after authentication")));

        String roleName = user.getRole().getName();

        return ResponseEntity.ok(ApiResponse.success("User logged in successfully!",
                new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), roleName)));
    }

    // Handles registration form submission from Thymeleaf
    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid RegisterRequest registerRequest,
                               RedirectAttributes redirectAttributes) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username is already taken!");
            return "redirect:/register";
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email is already in use!");
            return "redirect:/register";
        }

        // Create new user's account
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole(userRole);

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Registration successful!");
        return "redirect:/login";
    }

    // Logout API is typically handled client-side by deleting the JWT token.
    // For a backend-driven logout, token invalidation would be needed, which is more complex.
    // For now, we'll assume client-side token removal.
    // With Thymeleaf, logout is handled by Spring Security's /logout endpoint.
    // This method is kept for potential API clients.
    @PostMapping("/logout-api") // Renamed to avoid conflict with Spring Security's /logout
    public ResponseEntity<ApiResponse<String>> logoutUser() {
        return ResponseEntity.ok(ApiResponse.success("User logged out successfully (client-side token removal assumed)."));
    }
}
