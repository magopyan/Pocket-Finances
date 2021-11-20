package com.example.pocketexpenses.entities.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;

import java.util.List;

public class TransactionTypeWithSubtypes { // One TransactionTypes to many TransactionSubtypes

    @Embedded
    private TransactionType transactionType;
    @Relation(
            parentColumn = "id",
            entityColumn = "tran_type_id"
    )
    private List<TransactionSubtype> subtypesList;


   ///////////////////////////


    public TransactionTypeWithSubtypes(TransactionType transactionType, List<TransactionSubtype> subtypesList) {
        this.transactionType = transactionType;
        this.subtypesList = subtypesList;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public List<TransactionSubtype> getSubtypesList() {
        return subtypesList;
    }

    public void setSubtypesList(List<TransactionSubtype> subtypesList) {
        this.subtypesList = subtypesList;
    }
}
