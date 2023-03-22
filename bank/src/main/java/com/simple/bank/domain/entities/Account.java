package com.simple.bank.domain.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transaction> transactions;

    @Column(name = "balance", nullable = false)
    private double balance;
    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "account_type", nullable = false)
    private String account_type;

    @Column(name = "insert_date", nullable = false)
    private Date insert_date;

    public Account() {
    }

    public Account(User user, List<Transaction> transactions, double balance, boolean active, String account_type, Date insert_date) {
        this.user = user;
        this.transactions = transactions;
        this.balance = balance;
        this.active = active;
        this.account_type = account_type;
        this.insert_date = insert_date;
    }
    public Account(User user, double balance, boolean active, String account_type, Date insert_date) {
        this.user = user;
        this.balance = balance;
        this.active = active;
        this.account_type = account_type;
        this.insert_date = insert_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public Date getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(Date insert_date) {
        this.insert_date = insert_date;
    }
}
