package com.simple.bank.dto;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
public class AccountDTO {
    @NotEmpty
    private double amount;

    @NotEmpty
    private String accountType;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
