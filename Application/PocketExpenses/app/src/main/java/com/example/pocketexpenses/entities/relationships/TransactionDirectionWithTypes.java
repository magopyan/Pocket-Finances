package com.example.pocketexpenses.entities.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;

import java.util.List;

public class TransactionDirectionWithTypes {

    @Embedded
    private TransactionDirection transactionDirection;
    @Relation(
            parentColumn = "id",
            entityColumn = "tran_dir_id"
    )
    private List<TransactionType> typesList;


    ///////////////////////////////////////////


    public TransactionDirectionWithTypes(TransactionDirection transactionDirection, List<TransactionType> typesList) {
        this.transactionDirection = transactionDirection;
        this.typesList = typesList;
    }

    public TransactionDirection getTransactionDirection() {
        return transactionDirection;
    }

    public void setTransactionDirection(TransactionDirection transactionDirection) {
        this.transactionDirection = transactionDirection;
    }

    public List<TransactionType> getTypesList() {
        return typesList;
    }

    public void setTypesList(List<TransactionType> typesList) {
        this.typesList = typesList;
    }
}
