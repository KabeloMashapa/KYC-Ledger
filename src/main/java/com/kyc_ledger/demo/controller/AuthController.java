package com.kyc_ledger.demo.controller;
import com.kyc_ledger.demo.dto.ApiResponseDTO;
import com.kyc_ledger.demo.dto.LoginRequestDTO;
import com.kyc_ledger.demo.dto.LoginResponseDTO;
import com.kyc_ledger.demo.dto.RegisterRequestDTO;
import com.kyc_ledger.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")


public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    // POST api/auth/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<String>> register(
            @Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
    // Post api/auth/login
    @PostMapping("/login")
    public ResponseEntity <ApiResponseDTO<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
