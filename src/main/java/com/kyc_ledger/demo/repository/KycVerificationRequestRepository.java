package com.kyc_ledger.demo.repository;
import com.kyc_ledger.demo.model.Institution;
import com.kyc_ledger.demo.model.KycRecord;
import com.kyc_ledger.demo.model.KycVerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface KycVerificationRequestRepository extends JpaRepository<KycVerificationRequest,Long>{

    List<KycVerificationRequest> findByInstitution(Institution institution);
    List<KycVerificationRequest> findByKycRecord (KycRecord kycRecord);
    List<KycVerificationRequest> findByStatus(KycVerificationRequest.VerificationStatus status);
    Optional<KycVerificationRequest> findByBlockchainTxId(String blockchainTxId);

    // Check if institution already requested verification for this KYC
    boolean existsByInstitutionAndKycRecord(
            Institution institution,
            KycRecord kycRecord
    );
    // Get all pending requests for a specific institution
    @Query("SELECT v FROM KycVerificationRequest v" +
           "WHERE v.institution.id = :institutionId"+
           "AND v.status = 'PENDING'")
    List<KycVerificationRequest> findPendingByInstitutionId(Long institutionId);

    long countByInstitution(Institution institution);

}
