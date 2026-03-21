package com.kyc_ledger.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "kyc_records")

public class KycRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    // Unique KYC ID stored on blockchain
    @Column(nullable = false,unique = true)
    private String kycId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    // Personal information
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName ;
    @Column(nullable = false)
    private String dateOfBirth;
    @Column(nullable = false)
    private String nationality;
    @Column(nullable = false)
    private String idNumber;

    private String address ;
    private String city;
    private String country;

    // Blockchain reference
    @Column(unique = true)
    private String blockchainTxId; // Transaction ID
    @Column(unique = true)
    private String blockchainHash;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycStatus status; // Pending,Approved, Rejected, Expired
    private String rejectionReason;
    private LocalDateTime submittedAt ;
    private LocalDateTime reviewedAt;
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy ;

    @OneToMany(mappedBy = "kycRecord", cascade =  CascadeType.ALL)
    private List<Document> documents ;

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        status = KycStatus.PENDING;
    }
    public enum KycStatus {
        PENDING,UNDER_REVIEW,APPROVED,REJECTED,EXPIRED
    }




}
