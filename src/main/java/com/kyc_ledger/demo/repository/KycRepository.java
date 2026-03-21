package com.kyc_ledger.demo.repository;
import com.kyc_ledger.demo.model.KycRecord;
import com.kyc_ledger.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface KycRepository extends JpaRepository<KycRecord,Long> {

    Optional<KycRecord> findByKycId(String kycId);
    Optional<KycRecord> findByBlockchainTxId(String blockchainTxId);
    List<KycRecord> findByUser(User user);
    List<KycRecord> findByStatus(KycRecord.KycStatus status);
    Optional<KycRecord> findByUserAndStatus(User user, KycRecord.KycStatus status);
    boolean existsByKycId(String kycId);

    // Get all the KYC records for a user by their user ID
    @Query("SELECT k FROM KycRecord WHERE k.user = :userId")
    List<KycRecord> findAllByUserId (Long userId);
    // Count records by status
    long countByStatuss(KycRecord.KycStatus status);
}
