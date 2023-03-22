package com.simple.bank.service.impl;

import com.simple.bank.domain.entities.Account;
import com.simple.bank.repository.AccountRepository;
import com.simple.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> findAccountsByUserId(long id) {
        return accountRepository.findAccountsByUserId(id);
    }

    @Override
    public Account findAccountById(long id) {
        return accountRepository.findAccountById(id);
    }
}
