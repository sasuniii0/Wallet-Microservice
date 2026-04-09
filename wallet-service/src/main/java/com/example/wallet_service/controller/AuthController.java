package com.example.wallet_service.controller;

import com.example.wallet_service.dto.ApiResponseDTO;
import com.example.wallet_service.dto.LoginRequestDTO;
import com.example.wallet_service.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Login successful")
                        .data(Map.of("token", jwtUtil.generateToken(request.getEmail())))
                        .build()
        );
    }
}