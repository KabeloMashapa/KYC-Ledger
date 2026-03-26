package com.kyc_ledger.demo.service;
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

}
