package com.example.footballmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.util.List; // Không cần thiết nếu không có trường List<RoleDto>

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Role ID cannot be null")
    private Long roleId;

    private String roleName; // For display purposes

    // Đã xóa trường 'roles' và 'enabled'
    // private List<RoleDto> roles;
    // private Boolean enabled;
}
