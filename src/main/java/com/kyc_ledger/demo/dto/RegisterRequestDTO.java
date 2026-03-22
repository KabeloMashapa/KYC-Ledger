package com.kyc_ledger.demo.dto;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8 , message = "Password must at least 8 characters")
    private String password;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String role; // user, admin,
}
