package com.simple.bank.domain.enums;

public enum AccountTypes {
    CURRENT_ACCOUNT("Current Account"),
    SAVINGS_ACCOUNT("Savings Account");
    private String name;
    AccountTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
