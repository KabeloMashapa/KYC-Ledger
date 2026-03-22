package com.kyc_ledger.demo.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {

    private Long id;
    private String documentType;        // PASSPORT, NATIONAL_ID etc.
    private String fileName;
    private String fileHash;            // SHA-256 hash
    private String mimeType;
    private Long fileSize;
    private String documentStatus;      // UPLOADED, VERIFIED, REJECTED
    private LocalDateTime uploadedAt;
}
