package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.UserDto;
import com.example.footballmanagement.entity.Role;
import com.example.footballmanagement.entity.User;
import com.example.footballmanagement.repository.RoleRepository;
import com.example.footballmanagement.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
        return mapToDto(user);
    }

    public UserDto createUser(UserDto userDto, String password) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken: " + userDto.getUsername());
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use: " + userDto.getEmail());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(password)); // Encode password

        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id: " + userDto.getRoleId()));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));

        if (!existingUser.getUsername().equals(userDto.getUsername()) && userRepository.existsByUsername(userDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken: " + userDto.getUsername());
        }
        if (!existingUser.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use: " + userDto.getEmail());
        }

        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());

        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id: " + userDto.getRoleId()));
        existingUser.setRole(role);

        User updatedUser = userRepository.save(existingUser);
        return mapToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
        userRepository.delete(user);
    }

    private UserDto mapToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole().getId(), user.getRole().getName());
    }
}
