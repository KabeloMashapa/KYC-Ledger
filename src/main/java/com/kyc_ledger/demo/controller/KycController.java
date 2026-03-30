package com.kyc_ledger.demo.controller;
import com.kyc_ledger.demo.dto.KycRequestDTO;
import com.kyc_ledger.demo.dto.KycResponseDTO;
import com.kyc_ledger.demo.dto.ApiResponseDTO;
import com.kyc_ledger.demo.service.KycService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor

public class KycController {

    private final KycService kycService;
    // POST /api/kyc/submit
    @PostMapping("/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> submitKyc(
            @Valid @ResponseBody KycRequestDTO request
    ) {
        return ResponseEntity.ok(kycService.submitKyc(request));
    }
    // GET /api/kyc/{kycId}
    @GetMapping("/{kycId}")
    @PreAuthorize("hasRole('USER','ADMIN','INSTITUTION')")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> getKycId(
            @PathVariable String kycId
    ) {
        return ResponseEntity.ok(kycService.getKycById(kycId));
    }
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> getKycByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(kycService.getKycByStatus(status));

    }
    // PUT /api/kyc/{kycId}/approve
    @PutMapping("/{kycId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> approveKyc(
            @PathVariable String kycId, @RequestParam Long adminId,@RequestParam String reason
    ) {
        return ResponseEntity.ok(kycService.approveKyc(kycId,adminId));
    }




}
