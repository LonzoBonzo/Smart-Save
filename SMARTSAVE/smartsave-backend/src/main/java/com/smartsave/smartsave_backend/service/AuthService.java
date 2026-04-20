package com.smartsave.smartsave_backend.service;

import com.smartsave.smartsave_backend.domain.AppUser;
import com.smartsave.smartsave_backend.dto.AuthResponse;
import com.smartsave.smartsave_backend.dto.RegisterRequest;
import com.smartsave.smartsave_backend.dto.UserResponse;
import com.smartsave.smartsave_backend.exception.ApiException;
import com.smartsave.smartsave_backend.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "An account with that email already exists");
        }

        AppUser user = new AppUser();
        user.setFullName(request.fullName());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        AppUser savedUser = userRepository.save(user);
        return new AuthResponse("Registration successful", toUserResponse(savedUser));
    }

    public UserResponse toUserResponse(AppUser user) {
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole());
    }
}
