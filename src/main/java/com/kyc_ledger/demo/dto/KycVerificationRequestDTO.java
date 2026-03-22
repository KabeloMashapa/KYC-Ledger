package com.kyc_ledger.demo.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycVerificationRequestDTO {

    private Long id;
    private String institutionName;
    private String institutionCode;
    private String kycId;
    private String purpose;             // e.g. "Account Opening"
    private String status;              // PENDING, APPROVED, DENIED
    private String blockchainTxId;
    private LocalDateTime requestedAt;
    private LocalDateTime respondedAt;
}
