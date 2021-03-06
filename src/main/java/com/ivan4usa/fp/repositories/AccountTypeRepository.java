package com.ivan4usa.fp.repositories;

import com.ivan4usa.fp.entities.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    List<AccountType> findAccountTypesByUserId(Long userId);
}
