package com.example.wallet_service.controller;

import com.example.wallet_service.dto.ApiResponseDTO;
import com.example.wallet_service.util.JwtUtil;
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
    public ResponseEntity<ApiResponseDTO> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        String token = jwtUtil.generateToken(email);

        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Login successful")
                        .data(Map.of("token", token))
                        .build()
        );
    }
}