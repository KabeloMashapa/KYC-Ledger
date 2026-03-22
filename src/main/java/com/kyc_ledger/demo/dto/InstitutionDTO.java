package com.kyc_ledger.demo.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDTO {
    private Long id;
    private String institutionCode;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String country;
    private String type;                // BANK, INSURANCE etc.
    private String status;             // ACTIVE, SUSPENDED
    private String fabricMspId;
    private String fabricChannelName;
}
