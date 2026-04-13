package com.kyc_ledger.demo.service;
import com.google.protobuf.Api;
import com.google.rpc.context.AttributeContext;
import com.kyc_ledger.demo.dto.*;
import com.kyc_ledger.demo.exception.FabricException;
import com.kyc_ledger.demo.exception.UserNotFoundException;
import com.kyc_ledger.demo.exception.KycNotFoundException;
import com.kyc_ledger.demo.model.KycRecord;
import com.kyc_ledger.demo.model.User;
import com.kyc_ledger.demo.util.DateUtil;
import com.kyc_ledger.demo.util.HashUtil;
import com.kyc_ledger.demo.repository.KycRepository;
import com.kyc_ledger.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KycService {

    private final KycRecord kycRecord;
    private final UserRepository userRepository;
    private final FabricException fabricException;
    private final EmailService emailService;
    private final KycRepository kycRepository;
    private final FabricService fabricService;

    // Submit new KYC
    public ApiResponseDTO<KycResponseDTO> submitKyc(KycRequestDTO request) {
        User user = userRepository.findById(request.getUserId()
                ).orElseThrow(()-> new UserNotFoundException(request.getUserId()));

        // Check if the user has a approved or pending KYC request
        kycRepository.findByUserAndStatus(user, KycRecord.KycStatus.APPROVED).ifPresent(kyc -> {
            throw new RuntimeException("User already has an approved KYC");
        });
        String kycId = "KYC-" + UUID.randomUUID().toString().substring(0,8).toLowerCase();
        String kycHash = HashUtil.hashKycData(
                request.getFirstName(),
                request.getLastName(),
                request.getIdNumber(),
                request.getDateOfBirth(),
                request.getNationality()
        );
        String txId = fabricService.submitKycToBlockchain(kycId,kycHash);
        // Save the KYC record
        KycRecord kycRecord1 = new KycRecord();
        kycRecord1.setKycId(kycId);
        kycRecord1.setUser(user);
        kycRecord1.setId(user.getId());
        kycRecord1.setFirstName(request.getFirstName());
        kycRecord1.setLastName(request.getLastName());
        kycRecord1.setCity(request.getCity());
        kycRecord1.setAddress(request.getAddress());
        kycRecord1.setCountry(request.getCountry());
        kycRecord1.setBlockchainTxId(txId);
        kycRecord1.setBlockchainHash(kycHash);
        kycRecord1.setExpiresAt(DateUtil.calculateKycExpiry());
        kycRepository.save(kycRecord);
        emailService.sendKycSubmissionEmail(user.getEmail(), user.getFullName(),kycId);

        return ApiResponseDTO.success("Kyc Successfull",mapToDTO(kycRecord));

    }
    // Get KYC by Id
    public ApiResponseDTO<KycResponseDTO> getKycById(String kycId) {
        KycRecord kycRecord = kycRepository.findByKycId(kycId
        ).orElseThrow(()-> new KycNotFoundException("KycId",kycId));
        return ApiResponseDTO.success("KYC record found",mapToDTO(kycRecord));
    }
    // Get all Kyc Records for a user
    public ApiResponseDTO<List<KycResponseDTO>> getKycByUserId(Long userId) {
        List<KycRecord> records = kycRepository.findAllByUserId(userId);
        List<KycResponseDTO> dtos = records.stream(
                ).map(this::mapToDTO
                ).collect(Collectors.toList());
        return ApiResponseDTO.success("KYC records retrieved",dtos);
    }
    // Get all kyc records by status
    public ApiResponseDTO<List<KycResponseDTO>> getKycByStatus(String status) {
        KycRecord.KycStatus kycStatus = KycRecord.KycStatus.valueOf(status.toLowerCase());
        List<KycRecord> records = kycRepository.findByStatus(kycStatus);
        List<KycResponseDTO> dtos = records.stream(
               ).map(this::mapToDTO).collect(Collectors.toList());
        return ApiResponseDTO.success("KYC records retrieved ",dtos);

    }
    // Approve KYC
    public ApiResponseDTO<List<KycResponseDTO>> approveKyc(String kycId, Long adminId) {
        KycRecord kycRecord = kycRepository.findByKycId(kycId
        ).orElseThrow(() -> new KycNotFoundException("kycId",kycId));
        User admin = userRepository.findById(adminId
        ).orElseThrow(()-> new UserNotFoundException(adminId));
        fabricService.updateKycStatusOnBlockchain(kycId,"APPROVED");
        kycRecord.setStatus(KycRecord.KycStatus.APPROVED);
        kycRecord.setReviewedBy(admin);
        kycRecord.setReviewedAt(java.time.LocalDateTime.now());
        kycRepository.save(kycRecord);
        // Send approval email
        emailService.sendKycApprovedEmail(
                kycRecord.getUser().getEmail(),
                kycRecord.getUser().getFullName(),
                kycId
        );
        return ApiResponseDTO.success("Kyc Approved successfully",mapToDTO(kycRecord));

    }
    // Reject KYC
    public ApiResponseDTO<List<KycResponseDTO>>  rejectKyc(String kycId,Long adminId, String reason) {
        KycRecord kycRecord = kycRepository.findByKycId(kycId
        ).orElseThrow(() -> new KycNotFoundException("kycId",kycId));
        User admin = userRepository.findById(adminId
                ).orElseThrow(()-> new KycNotFoundException(adminId));
        fabricService.updateKycStatusOnBlockchain(kycId,"REJECTED");
        kycRecord.setStatus(KycRecord.KycStatus.REJECTED);
        kycRecord.setReviewedBy(admin);
        kycRecord.setReviewedAt(java.time.LocalDateTime.now());
        kycRepository.save(kycRecord);
        // Send rejected email
        emailService.sendKycRejectedEmail(
                kycRecord.getUser().getEmail(),
                kycRecord.getUser().getFullName(),
                kycId
        );
        return ApiResponseDTO.success("Kyc Rejected",mapToDTO(kycRecord));

    }
    // Verify KYC on blockchain
    public ApiResponseDTO<Boolean> verifyKycOnBlockchain(String kycId) {
        KycRecord kycRecord = kycRepository.findByKycId(kycId
        ).orElseThrow(()-> new KycNotFoundException("kycId",kycId));
        boolean isValid = fabricService.verifyKycOnBlockchain(
                kycId,
                kycRecord.getBlockchainHash()
        );
        return ApiResponseDTO.success(
                isValid ? "KYC is valid on blockchain" : "KYC verification failed",
                isValid
        );
    }
    // Map KycRecord entity to KycResponseDTO
    private KycResponseDTO mapToDTO(KycRecord record) {
        KycResponseDTO dto = new KycResponseDTO();
        dto.setId(record.getId());
        dto.setKycId(record.getKycId());
        dto.setFullName(record.getFirstName() + " " + record.getLastName());
        dto.setIdNumber(record.getIdNumber());
        dto.setNationality(record.getNationality());
        dto.setStatus(record.getStatus().name());
        dto.setBlockchainTxId(record.getBlockchainTxId());
        dto.setBlockchainHash(record.getBlockchainHash());
        dto.setRejectionReason(record.getRejectionReason());
        dto.setSubmittedAt(record.getSubmittedAt());
        dto.setReviewedAt(record.getReviewedAt());
        dto.setExpiresAt(record.getExpiresAt());
        return dto;
    }

}
