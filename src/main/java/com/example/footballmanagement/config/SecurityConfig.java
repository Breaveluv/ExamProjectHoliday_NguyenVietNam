package com.example.footballmanagement.config;

import com.example.footballmanagement.security.JwtAuthEntryPoint;
import com.example.footballmanagement.security.JwtAuthenticationFilter;
import com.example.footballmanagement.security.JwtTokenProvider;
import com.example.footballmanagement.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthEntryPoint unauthorizedHandler;
    private final JwtTokenProvider jwtTokenProvider; // Inject JwtTokenProvider

    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                          JwtAuthEntryPoint unauthorizedHandler,
                          JwtTokenProvider jwtTokenProvider) { // Add JwtTokenProvider to constructor
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Define JwtAuthenticationFilter as a Bean
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilterBean() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

    // --- Security Filter Chain for API Endpoints (Stateless, JWT-based) ---
    @Bean
    @Order(1) // Process this filter chain first
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for API
            .securityMatcher("/api/**", "/auth/login-api", "/auth/logout-api") // Apply only to API paths
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless for API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/dashboard").permitAll() // Allow public access to dashboard API
                .requestMatchers("/auth/login-api", "/auth/logout-api", "/auth/register").permitAll() // Allow auth API endpoints
                .anyRequest().authenticated() // All other API requests require authentication
            );

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // --- Security Filter Chain for UI Endpoints (Session-based, Form Login) ---
    @Bean
    @Order(2) // Process this filter chain second
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/auth/register") // Allow POST to /auth/register without CSRF token for Thymeleaf form
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll() // Allow static resources
                .requestMatchers("/login", "/register").permitAll() // Allow access to login/register pages
                .anyRequest().authenticated() // All other UI requests require authentication
            )
            .formLogin(form -> form
                .loginPage("/login") // Custom login page
                .loginProcessingUrl("/perform_login") // URL to submit username and password
                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard after successful login
                .failureUrl("/login?error=true") // Redirect to login page with error on failure
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL to trigger logout
                .logoutSuccessUrl("/login?logout=true") // Redirect to login page after logout
                .deleteCookies("JSESSIONID") // Delete session cookie
                .permitAll()
            );

        http.authenticationProvider(authenticationProvider());
        // No JWT filter for UI chain, it uses session

        return http.build();
    }
}
