package com.kyc_ledger.demo.repository;
import com.kyc_ledger.demo.model.KycRecord;
import com.kyc_ledger.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;
public interface DocumentRepository extends JpaRepository<KycRecord,Long> {

    Optional<KycRecord> findByUserRecord(String user);
}
