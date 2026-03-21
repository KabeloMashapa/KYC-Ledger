package com.kyc_ledger.demo.repository;
import com.kyc_ledger.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail (String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email) ;
    boolean existsByPhoneNumber(String phoneNumber);
    List<User> findByRole(User.Role role);
}
