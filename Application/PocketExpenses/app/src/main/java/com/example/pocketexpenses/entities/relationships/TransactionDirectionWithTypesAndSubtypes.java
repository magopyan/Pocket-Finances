package com.example.pocketexpenses.entities.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionType;

import java.util.List;

public class TransactionDirectionWithTypesAndSubtypes {  // One TransactionDirection to many TransactionTypes, each with its list of Subtypes

    @Embedded
    private TransactionDirection transactionDirection;
    @Relation(
            parentColumn = "id",
            entityColumn = "tran_dir_id",
            entity = TransactionType.class
    )
    private List<TransactionTypeWithSubtypes> typeWithSubtypesList;


    ///////////////////////////////////////////


    public TransactionDirectionWithTypesAndSubtypes(TransactionDirection transactionDirection, List<TransactionTypeWithSubtypes> typeWithSubtypesList) {
        this.transactionDirection = transactionDirection;
        this.typeWithSubtypesList = typeWithSubtypesList;
    }

    public TransactionDirection getTransactionDirection() {
        return transactionDirection;
    }

    public void setTransactionDirection(TransactionDirection transactionDirection) {
        this.transactionDirection = transactionDirection;
    }

    public List<TransactionTypeWithSubtypes> getTypeWithSubtypesList() {
        return typeWithSubtypesList;
    }

    public void setTypesWithSubtypesList(List<TransactionTypeWithSubtypes> typeWithSubtypesList) {
        this.typeWithSubtypesList = typeWithSubtypesList;
    }

    public void setTypeWithSubtypesList(List<TransactionTypeWithSubtypes> typeWithSubtypesList) {
        this.typeWithSubtypesList = typeWithSubtypesList;
    }
}
