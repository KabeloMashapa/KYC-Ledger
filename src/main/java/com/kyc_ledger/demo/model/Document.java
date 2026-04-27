package com.kyc_ledger.demo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    public Document() {}
    public Document(Long id,KycRecord kycRecord, DocumentType documentType,
                    String fileName, String filePath, String fileHash,
                    String mimeType, Long fileSize) {
        this.id = id ;
        this.kycRecord = kycRecord;
        this.documentType = documentType;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileHash = fileHash;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
    }
    public Long getId() {
        return id;
    }
    public KycRecord getKycRecord() {
        return kycRecord;
    }
    public DocumentType getDocumentType() {
        return documentType;
    }
    public String getFileName() {
        return fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public String getFileHash() {
        return fileHash;
    }
    public String getMimeType() {
        return mimeType;
    }
    public Long getFileSize() {
        return fileSize;
    }
    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setId(Long id){
        this.id = id ;
    }
    public void setKycRecord(KycRecord kycRecord) {
        this.kycRecord = kycRecord;
    }
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

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


