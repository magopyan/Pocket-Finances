package com.example.pocketexpenses.entities.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;

import java.util.List;

public class TransactionSubtypeWithTransactions { // One TransactionSubtype to many Transactions

    @Embedded
    private TransactionSubtype transactionSubtype; // i ot tuk moje da se izvleche TransactionType ako trqbva s nego da se pravqt spravki
    @Relation(
            parentColumn = "id",
            entityColumn = "tran_subtype_id"
    )
    private List<Transaction> transactionList;


    /////////////////////////////////////////////////


    public TransactionSubtypeWithTransactions(TransactionSubtype transactionSubtype, List<Transaction> transactionList) {
        this.transactionSubtype = transactionSubtype;
        this.transactionList = transactionList;
    }

    public TransactionSubtype getTransactionSubtype() {
        return transactionSubtype;
    }

    public void setTransactionSubtype(TransactionSubtype transactionSubtype) {
        this.transactionSubtype = transactionSubtype;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
