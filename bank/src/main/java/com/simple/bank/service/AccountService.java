package com.simple.bank.service;

import com.simple.bank.domain.entities.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAccountsByUserId(long id);
    Account findAccountById(long id);
}
