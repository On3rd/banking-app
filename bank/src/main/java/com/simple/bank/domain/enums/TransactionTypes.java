package com.simple.bank.domain.enums;

public enum TransactionTypes {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER("Transfer");
    private String name;

    TransactionTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
