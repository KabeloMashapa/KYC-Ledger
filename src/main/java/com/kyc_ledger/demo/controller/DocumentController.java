package com.kyc_ledger.demo.controller;
import com.kyc_ledger.demo.dto.ApiResponseDTO;
import com.kyc_ledger.demo.dto.DocumentDTO;
import com.kyc_ledger.demo.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/documents")


public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {

        this.documentService = documentService;

    }
    // POST /api/documents/upload
    @PostMapping("/upload/{kycId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponseDTO<DocumentDTO>> uploadDocuments (
            @PathVariable String kycId, @RequestParam("file") MultipartFile file, @RequestParam("documentType") String documentType
    ) {
        return ResponseEntity.ok(documentService.uploadDocument(kycId,file,documentType));

    }
    // GET /api/documents/upload
    @GetMapping("/{kycId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','INSTITUTION')")
    public ResponseEntity<ApiResponseDTO<List<DocumentDTO>>> getDocuments(
            @PathVariable String kycId
    ) {
        return ResponseEntity.ok(documentService.getDocumentsByKycId(kycId));
    }
    // POST /verify/documents
    @PostMapping("/verify/{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN','INSTITUTION')")
    public ResponseEntity<ApiResponseDTO<Boolean>> verifyDocument(
            @PathVariable Long documentId, @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(documentService.verifyDocument(documentId,file));
    }
}
