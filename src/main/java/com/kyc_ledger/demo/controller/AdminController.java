package com.kyc_ledger.demo.controller;
import com.kyc_ledger.demo.dto.KycResponseDTO;
import com.kyc_ledger.demo.dto.ApiResponseDTO;
import com.kyc_ledger.demo.service.KycService;
import com.kyc_ledger.demo.repository.KycRepository;
import com.kyc_ledger.demo.repository.UserRepository;
import com.kyc_ledger.demo.model.KycRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ADMIN')")

public class AdminController {

    private final KycRepository kycRepository;
    private final UserRepository userRepository;
    private final KycService kycService;

    public AdminController(KycRepository kycRepository,
                           UserRepository userRepository,
                           KycService kycService) {
        this.kycRepository = kycRepository;
        this.userRepository = userRepository;
        this.kycService = kycService;
    }
    // GET /admin/password
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponseDTO<Map<String, Long>>> getDashBoard() {

        Map<String,Long> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalKyc", kycRepository.count());
        stats.put("pendingKyc",kycRepository.countByStatuss(KycRecord.KycStatus.PENDING));
        stats.put("approvedKyc",kycRepository.countByStatuss(KycRecord.KycStatus.APPROVED));
        stats.put("rejectedKyc",kycRepository.countByStatuss(KycRecord.KycStatus.REJECTED));

        return ResponseEntity.ok(ApiResponseDTO.success("DashBoard stats",stats));
    }
    // GET /admin/kyc/pending
    @GetMapping("/kyc/pending")
    public ResponseEntity<ApiResponseDTO<List<KycResponseDTO>>> getPendingKyc() {
        return ResponseEntity.ok(kycService.getKycByStatus("Pending"));
    }
    // GET /admin/kyc/all
    @GetMapping("/kyc/all")
    public ResponseEntity<ApiResponseDTO<List<KycResponseDTO>>> getAllKyc() {
        return ResponseEntity.ok(kycService.getKycByStatus("Pending"));
    }
    // PUT /api/admin/kyc/{kycId}/approve
    @PutMapping("/kyc/{kycId}/approve")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> approveKyc(
            @PathVariable String kycId,
            @RequestParam Long adminId
    ) {
        return ResponseEntity.ok(kycService.approveKyc(kycId,adminId));

    }
    // PUT /api/admin/kyc/{kycId}/reject
    @PutMapping("/kyc/{kycId}/reject")
    public ResponseEntity<ApiResponseDTO<KycResponseDTO>> rejectKyc(
            @PathVariable String kycId,
            @RequestParam Long adminId,
            @RequestParam String reason
    ) {
        return ResponseEntity.ok(kycService.rejectKyc(kycId,adminId,reason));
    }
}
