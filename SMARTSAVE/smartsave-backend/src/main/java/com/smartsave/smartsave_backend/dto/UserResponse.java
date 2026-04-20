package com.smartsave.smartsave_backend.dto;

import com.smartsave.smartsave_backend.domain.Role;

public record UserResponse(
    Long id,
    String fullName,
    String email,
    Role role
) {
}
