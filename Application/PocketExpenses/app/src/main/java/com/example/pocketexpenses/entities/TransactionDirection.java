package com.example.pocketexpenses.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.entities.relationships.TransactionTypeWithSubtypes;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "transaction_direction")
public class TransactionDirection {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int coefficient; // za transfer kakuv shte e? NULL?

    @Ignore
    private List<TransactionType> transactionTypesList = null;


    //////////////////////////

    public TransactionDirection() {}

    @Ignore
    public TransactionDirection(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
    }

    @Ignore
    public TransactionDirection(String name, int coefficient, List<TransactionType> transactionTypesList) {
        super();
        this.name = name;
        this.coefficient = coefficient;
        this.transactionTypesList = transactionTypesList;
    }

    public TransactionDirection(TransactionDirectionWithTypesAndSubtypes tranDirWithTypesAndSubtypes) {
        this.id = tranDirWithTypesAndSubtypes.getTransactionDirection().getId();
        this.name = tranDirWithTypesAndSubtypes.getTransactionDirection().getName();
        this.coefficient = tranDirWithTypesAndSubtypes.getTransactionDirection().getCoefficient();
        this.transactionTypesList = this.getTransactionTypesList(tranDirWithTypesAndSubtypes.getTypeWithSubtypesList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public List<TransactionType> getTransactionTypesList() {
        return transactionTypesList;
    }

    public void setTransactionTypesList(List<TransactionType> transactionTypesList) {
        this.transactionTypesList = transactionTypesList;
    }

    public void setTransactionType(TransactionType transactionType) {
        if (this.transactionTypesList == null) {
            this.transactionTypesList = new ArrayList<>();
        }
        this.transactionTypesList.add(transactionType);
    }

    private List<TransactionType> getTransactionTypesList(List<TransactionTypeWithSubtypes> transactionTypeWithSubtypes) {
        for (TransactionTypeWithSubtypes tranTypeWithSubtypes : transactionTypeWithSubtypes) {
            TransactionType transactionType = tranTypeWithSubtypes.getTransactionType();
            transactionType.setTransactionSubtypeList(tranTypeWithSubtypes.getSubtypesList());
            this.setTransactionType(transactionType);
        }
        return this.transactionTypesList;
    }
}
