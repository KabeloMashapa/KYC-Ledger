package com.kyc_ledger.demo.dto;
import java.time.LocalDateTime;


public class DocumentDTO {

    private Long id;
    private String documentType;        // PASSPORT, NATIONAL_ID etc.
    private String fileName;
    private String fileHash;            // SHA-256 hash
    private String mimeType;
    private Long fileSize;
    private String documentStatus;      // UPLOADED, VERIFIED, REJECTED
    private LocalDateTime uploadedAt;

    public DocumentDTO(Long id,String documentType,String fileName, String fileHash,
                       String mimeType, Long fileSize, String documentStatus,
                       LocalDateTime uploadedAt) {
        this.id = id;
        this.documentType = documentType;
        this.fileName = fileName;
        this.fileHash = fileHash;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.documentStatus = documentStatus;
        this.uploadedAt = uploadedAt;
    }
    public DocumentDTO() {}

    public Long getId() {
        return id;
    }
    public String getDocumentType() {
        return documentType;
    }
    public String getFileName() {
        return fileName;
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
    public String getDocumentStatus() {
        return documentStatus;
    }
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
