package com.kyc_ledger.demo.repository;
import com.kyc_ledger.demo.model.Document;
import com.kyc_ledger.demo.model.KycRecord;
import com.kyc_ledger.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface DocumentRepository extends JpaRepository<Document,Long> {

    List<Document> findByKycRecord(KycRecord kycRecord);
    List<Document> findByKycRecordAndDocumentType(
            KycRecord kycRecord,
            Document.DocumentType documentType);
    Optional<Document> findByFileHash (String fileHash);
    boolean existsByFileHash (String fileHash);
    List<Document> findByDocumentStatus(Document.DocumentStatus status);


}
