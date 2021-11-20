package com.example.pocketexpenses.entities.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.Transaction;

import java.util.List;

public class AccountWithTransactions {

    @Embedded
    private Account account;
    @Relation(
            parentColumn = "id",
            entityColumn = "account_id"
    )
    private List<Transaction> transactionList;

    ////////////////////////////////////////////////


    public AccountWithTransactions(Account account, List<Transaction> transactionList) {
        this.account = account;
        this.transactionList = transactionList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
