package com.simple.bank.domain.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name= "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "account_no")
    private String account_no;

    @Column(name = "amount")
    private double amount;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "transaction_type", nullable = false)
    private String transaction_type;

    @Column(name = "insert_date", nullable = false)
    private Date insert_date;

    public Transaction() {
    }

    public Transaction(Account account, String account_no, double amount, String comment, String transaction_type, Date insert_date) {
        this.account = account;
        this.account_no = account_no;
        this.amount = amount;
        this.comment = comment;
        this.transaction_type = transaction_type;
        this.insert_date = insert_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public Date getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(Date insert_date) {
        this.insert_date = insert_date;
    }
}
