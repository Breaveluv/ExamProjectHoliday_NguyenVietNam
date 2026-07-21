package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.RoleDto;
import com.example.footballmanagement.entity.Role;
import com.example.footballmanagement.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id: " + id));
        return mapToDto(role);
    }

    public RoleDto createRole(RoleDto roleDto) {
        if (roleRepository.findByName(roleDto.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role with name " + roleDto.getName() + " already exists.");
        }
        Role role = new Role();
        role.setName(roleDto.getName());
        Role savedRole = roleRepository.save(role);
        return mapToDto(savedRole);
    }

    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id: " + id));

        if (roleRepository.findByName(roleDto.getName()).isPresent() && !roleDto.getName().equals(existingRole.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role with name " + roleDto.getName() + " already exists.");
        }

        existingRole.setName(roleDto.getName());
        Role updatedRole = roleRepository.save(existingRole);
        return mapToDto(updatedRole);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id: " + id));
        roleRepository.delete(role);
    }

    private RoleDto mapToDto(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }
}
