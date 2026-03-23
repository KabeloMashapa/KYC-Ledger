package com.kyc_ledger.demo.repository;
import com.kyc_ledger.demo.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution,Long> {
    Optional<Institution> findByInstitutionCode(String institutionCode);
    Optional<Institution> findByEmail(String email);
    Optional<Institution> findByFabricMspId(String fabricMspId);
    boolean existsByInstitutionCode(String institutionCode);
    boolean existsByEmail(String email);
    List<Institution> findByStatus(Institution.InstitutionStatus status);
    List<Institution> findByType(Institution.InstitutionType type);
}
