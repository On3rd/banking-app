package com.simple.bank.repository;

import com.simple.bank.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findAccountsByUserId(long id);
    Account findAccountById(long id);
}
