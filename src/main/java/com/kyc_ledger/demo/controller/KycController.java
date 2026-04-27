package com.kyc_ledger.demo.controller;
import com.kyc_ledger.demo.dto.KycRequestDTO;
import com.kyc_ledger.demo.dto.KycResponseDTO;
import com.kyc_ledger.demo.dto.ApiResponseDTO;
import com.kyc_ledger.demo.service.KycService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/kyc")


public class KycController {

    private final KycService kycService;

    public KycController(KycService kycService) {
        this.kycService = kycService;
    }
    // POST /api/kyc/submit
    @PostMapping("/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> submitKyc(
            @Valid @RequestBody KycRequestDTO request
    ) {
        return ResponseEntity.ok(kycService.submitKyc(request));
    }
    // GET /api/kyc/{kycId}
    @GetMapping("/{kycId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','INSTITUTION')")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> getKycById(
            @PathVariable String kycId
    ) {
        return ResponseEntity.ok(kycService.getKycById(kycId));
    }
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity <ApiResponseDTO<List<KycResponseDTO>>> getKycUserId(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(kycService.getKycByUserId(userId));
    }
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <ApiResponseDTO<List<KycResponseDTO>>> getKycByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(kycService.getKycByStatus(status));

    }
    // PUT /api/kyc/{kycId}/approve
    @PutMapping("/{kycId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> approveKyc(
            @PathVariable String kycId, @RequestParam Long adminId
    ) {
        return ResponseEntity.ok(kycService.approveKyc(kycId,adminId));
    }
    // PUT /api/kyc/{kycId}/reject
    @PutMapping("/{kycId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> rejectKyc(
            @PathVariable String kycId,@RequestParam Long adminId,@RequestParam String reason
    ) {
        return ResponseEntity.ok(kycService.rejectKyc(kycId,adminId,reason));
    }
    // GET /api/kyc/{kycId}/verify
    @GetMapping("/{kycId}/verify")
    @PreAuthorize("hasRole('ADMIN','INSTITUTION')")
    public ResponseEntity<ApiResponseDTO<Boolean>> verifyKyc(
            @PathVariable String kycId
    ) {
        return ResponseEntity.ok(kycService.verifyKycOnBlockchain(kycId));
    }




}
