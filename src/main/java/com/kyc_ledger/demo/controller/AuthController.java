package com.kyc_ledger.demo.controller;
import com.kyc_ledger.demo.dto.ApiResponseDTO;
import com.kyc_ledger.demo.dto.LoginRequestDTO;
import com.kyc_ledger.demo.dto.LoginResponseDTO;
import com.kyc_ledger.demo.dto.RegisterRequestDTO;
import com.kyc_ledger.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;
    // POST api/auth/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<String>> register(
            @Valid @ResponseBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
    // Post api/auth/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<String>> login(
            @Valid @ResponseBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
