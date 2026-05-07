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
@Table(name="institutions")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String institutionCode;
    @Column(nullable = false)
    private String name ;
    @Column(nullable = false, unique = true)
    private String email;
    private String phoneNumber;
    private String address;
    private String country;

    @Enumerated(EnumType.STRING)
    private InstitutionType type ;
    @Enumerated(EnumType.STRING)
    private InstitutionStatus status;

    // Fabric network identity
    private String fabricMspId;
    private String fabricChannelName;

    @OneToMany(mappedBy = "institution",cascade = CascadeType.ALL)
    private List<KycVerificationRequest> verificationRequests;
    private LocalDateTime registeredAt;

    public Institution() {}

    public Institution(Long id,String institutionCode, String name,
                       String email, String phoneNumber, String address,
                       String country, InstitutionType type, InstitutionStatus status,
                       String fabricMspId, String fabricChannelName, List<KycVerificationRequest> verificationRequests,
                       LocalDateTime registeredAt) {
        this.id = id;
        this.institutionCode = institutionCode;
        this.name = name ;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.country = country;
        this.type = type;
        this.status = status;
        this.fabricMspId = fabricMspId;
        this.fabricChannelName = fabricChannelName;
    }

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        status = InstitutionStatus.ACTIVE;
    }
    public enum InstitutionType {
        BANK,INSURANCE,TELECOM,GOVERNMENT,OTHER
    }
    public enum InstitutionStatus {
        ACTIVE,SUSPENDED,DEREGISTERED
    }

}
