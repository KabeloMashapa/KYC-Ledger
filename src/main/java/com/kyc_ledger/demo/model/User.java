package com.kyc_ledger.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String password ;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable= false,updatable = false)
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<KycRecord> kycRecords ;

    public User(Long id, String email, String password, String fullName, String phoneNumber, Role role,
                LocalDateTime createdAt,LocalDateTime updatedAt,  List<KycRecord> kycRecords) {
        this.id = id ;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.kycRecords = kycRecords;
    }
    public Long getId() {return id;}
    public String getEmail() {return  email;}
    public String getPassword() {return password;}
    public String getFullName() {return fullName;}
    public String getPhoneNumber() {return phoneNumber;}
    public Role getRole() {return role;}
    public LocalDateTime getCreatedAt() {return createdAt;}
    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public List<KycRecord> getKycRecords() {return kycRecords;}

    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setRole(Role role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setKycRecords(List<KycRecord> kycRecords) { this.kycRecords = kycRecords; }
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    public enum Role {
        USER,ADMIN,INSTITUTION
    }


}

