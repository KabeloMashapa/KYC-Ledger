package com.kyc_ledger.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    public KycRecord(){

    }
    public KycRecord(Long id, String kycId, User user, String firstName,
                     String lastName, String dateOfBirth,String nationality,
                     String idNumber, String address, String city, String country,
                     String blockchainTxId, String blockchainHash, KycStatus status,
                     String rejectionReason, LocalDateTime submittedAt,
                     LocalDateTime reviewedAt, LocalDateTime expiresAt,
                     User reviewedBy, List<Document> documents) {
        this.id = id;
        this.kycId = kycId;
        this.user = user ;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.idNumber = idNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.blockchainTxId = blockchainTxId;
        this.blockchainHash = blockchainHash;
        this.status = status;
        this.rejectionReason =rejectionReason;
        this.submittedAt = submittedAt;
        this.reviewedAt = reviewedAt;
        this.expiresAt = expiresAt;
        this.reviewedBy = reviewedBy;
        this.documents = documents;
    }
    public Long getId() { return id;}
    public String getKycId() { return kycId;}
    public User getUser() { return user; }
    public String getFirstName() { return firstName;}
    public String getLastName() { return lastName;}
    public String getDateOfBirth() { return dateOfBirth; }
    public String getNationality() { return nationality; }
    public String getIdNumber() { return idNumber; }
    public String getAddress() { return address; }
    public String getCity() { return city;}
    public String getCountry() { return country;}
    public String getBlockchainTxId() { return blockchainTxId; }
    public String getBlockchainHash() { return blockchainHash; }
    public KycStatus getStatus() { return status; }
    public String getRejectionReason() { return rejectionReason; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public User getReviewedBy() { return reviewedBy; }
    public List<Document> getDocuments() { return documents; }

    public void setId(Long id) { this.id = id; }
    public void setKycId(String kycId) { this.kycId = kycId; }
    public void setUser(User user) { this.user = user; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setCountry(String country) { this.country = country; }
    public void setBlockchainTxId(String blockchainTxId) { this.blockchainTxId = blockchainTxId; }
    public void setBlockchainHash(String blockchainHash) { this.blockchainHash = blockchainHash; }
    public void setStatus(KycStatus status) { this.status = status; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setReviewedBy(User reviewedBy) { this.reviewedBy = reviewedBy; }
    public void setDocuments(List<Document> documents) { this.documents = documents; }


    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        status = KycStatus.PENDING;
    }
    public enum KycStatus {
        PENDING,UNDER_REVIEW,APPROVED,REJECTED,EXPIRED
    }


}
