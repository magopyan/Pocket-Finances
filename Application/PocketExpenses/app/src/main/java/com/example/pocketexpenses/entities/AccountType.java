package com.example.pocketexpenses.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_type")
public class AccountType {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    //////////////////////////

    public AccountType(String name) {
        this.name = name;
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
}
