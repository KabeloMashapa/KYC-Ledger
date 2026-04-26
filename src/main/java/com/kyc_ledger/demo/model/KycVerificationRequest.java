package com.kyc_ledger.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "kyc_verification_requests")
public class KycVerificationRequest {

    public KycVerificationRequest(Long id, Institution institution,
                                  KycRecord kycRecord, VerificationStatus status,
                                  String blockchainTxId, String purpose,
                                  LocalDateTime requestedAt,
                                  LocalDateTime respondedAt) {
        this.id = id;
        this.institution = institution;
        this.kycRecord = kycRecord;
        this.status = status;
        this.blockchainTxId = blockchainTxId;
        this.purpose = purpose;
        this.requestedAt = requestedAt;
        this.respondedAt = respondedAt;
    }
    public Long getId() { return id; }
    public Institution getInstitution() { return institution; }
    public KycRecord getKycRecord() { return kycRecord; }
    public VerificationStatus getStatus() { return status; }
    public String getBlockchainTxId() { return blockchainTxId; }
    public String getPurpose() { return purpose; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public LocalDateTime getRespondedAt() { return respondedAt; }

    public void setId(Long id) { this.id = id; }
    public void setInstitution(Institution institution) { this.institution = institution; }
    public void setKycRecord(KycRecord kycRecord) { this.kycRecord = kycRecord; }
    public void setStatus(VerificationStatus status) { this.status = status; }
    public void setBlockchainTxId(String blockchainTxId) { this.blockchainTxId = blockchainTxId; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }
    public void setRespondedAt(LocalDateTime respondedAt) { this.respondedAt = respondedAt; }
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
