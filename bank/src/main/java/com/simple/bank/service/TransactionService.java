package com.simple.bank.service;

import com.simple.bank.domain.entities.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findTransactionByAccountId(long id);
}
