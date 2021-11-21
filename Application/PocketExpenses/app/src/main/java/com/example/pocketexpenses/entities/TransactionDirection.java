package com.example.pocketexpenses.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "transaction_direction")
public class TransactionDirection {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int coefficient; // za transfer kakuv shte e? NULL?


    //////////////////////////

    public TransactionDirection(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
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
}
