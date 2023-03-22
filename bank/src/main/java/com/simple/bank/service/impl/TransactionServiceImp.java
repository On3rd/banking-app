package com.simple.bank.service.impl;

import com.simple.bank.domain.entities.Transaction;
import com.simple.bank.repository.TransactionRepository;
import com.simple.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImp implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public List<Transaction> findTransactionByAccountId(long id) {
        return transactionRepository.findTransactionByAccountId(id);
    }
}
