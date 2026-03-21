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
@Table(name = "documents")

public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kyc_record_id",nullable = false)
    private KycRecord kycRecord;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType; // Passport, Id_card
    @Column(nullable = false)
    private String fileName ;
    @Column(nullable = false)
    private String filePath ; // Local cloud storage path
    @Column(nullable = false)
    private String fileHash ; // SHA-256 hash stored on the blockchain
    private String mimeType;
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus; // Uploaded, verified, etc
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
        documentStatus = DocumentStatus.UPLOADED;
    }

    public enum DocumentType {
        PASSPORT,NATIONAL_ID,DRIVERS_LICENSE,UTILITY_BILL,BANK_STATEMENT,SELFIE_WITH_ID
    }
    public enum DocumentStatus {
        UPLOADED,VERIFIED,REJECTED
    }
}


