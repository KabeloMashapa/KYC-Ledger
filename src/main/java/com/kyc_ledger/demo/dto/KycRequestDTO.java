package com.kyc_ledger.demo.dto;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class KycRequestDTO {

    @NotBlank(message="First Name is required")
    private String firstName ;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @NotBlank(message = "Date of Birth is required")
    private String dateOfBirth;
    @NotBlank(message = "Nationality is required")
    private String nationality;
    @NotBlank(message = "ID number is required")
    private String idNumber;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "User ID is required")
    private String userId;
}
