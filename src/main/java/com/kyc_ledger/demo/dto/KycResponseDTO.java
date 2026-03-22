package com.kyc_ledger.demo.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycResponseDTO {

    private Long id;
    private String kycId;               // Unique KYC ID
    private String fullName;
    private String idNumber;
    private String nationality;
    private String status;              // PENDING, APPROVED, REJECTED etc.
    private String blockchainTxId;      // Fabric transaction ID
    private String blockchainHash;      // Hash on blockchain
    private String rejectionReason;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
    private LocalDateTime expiresAt;
    private List<DocumentDTO> documents;

}
