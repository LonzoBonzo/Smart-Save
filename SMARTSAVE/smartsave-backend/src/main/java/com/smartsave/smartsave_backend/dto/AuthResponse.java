package com.smartsave.smartsave_backend.dto;

public record AuthResponse(
    String message,
    UserResponse user
) {
}
