package com.kyc_ledger.demo.service;
import com.kyc_ledger.demo.dto.*;
import com.kyc_ledger.demo.exception.ResourceAlreadyExistsException;
import com.kyc_ledger.demo.exception.UserNotFoundException;
import com.kyc_ledger.demo.model.KycRecord;
import com.kyc_ledger.demo.model.User;
import com.kyc_ledger.demo.repository.UserRepository;

import com.kyc_ledger.demo.security.JwtTokenProvider;
import com.kyc_ledger.demo.util.DateUtil;
import com.kyc_ledger.demo.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public ApiResponseDTO<String> register(RegisterRequestDTO request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("User","email",request.getEmail());
        }
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResourceAlreadyExistsException("User","phoneNumber",request.getPhoneNumber());
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(User.Role.valueOf(
                request.getRole() != null ? request.getRole() : "USER"));
        userRepository.save(user);
        return ApiResponseDTO.success("User requested successfully",null);

    }
    public ApiResponseDTO<LoginResponseDTO> login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String token = jwtTokenProvider.generateToken(request.getEmail());
        User user = userRepository.findByEmail(request.getEmail()
                ).orElseThrow(() -> new RuntimeException("User not found"));
        LoginRequestDTO  response = new LoginRequestDTO(
                token,
                "Bearer",
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name()
        );
        return ApiResponseDTO.success("Login successful",response);
    }



}
