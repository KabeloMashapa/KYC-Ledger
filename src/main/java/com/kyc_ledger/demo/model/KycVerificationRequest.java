package com.kyc_ledger.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kyc_verification_requests")
public class KycVerificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id",nullable = false)
    private Institution institution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kyc_record_id",nullable = false)
    private KycRecord kycRecord;

    @Enumerated(EnumType.STRING)
    private VerificationStatus status;

    private String blockchainTxId;
    private String purpose;
    private LocalDateTime requestedAt;
    private LocalDateTime respondedAt;

    @PrePersist
    protected void onCreate() {
        requestedAt = LocalDateTime.now();
        status = VerificationStatus.PENDING;
    }
    public enum VerificationStatus {
        PENDING,APPROVED,DENIED
    }
}
