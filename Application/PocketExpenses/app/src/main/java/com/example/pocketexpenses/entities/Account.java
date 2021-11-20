package com.example.pocketexpenses.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "account",
        foreignKeys = @ForeignKey(entity = AccountType.class, parentColumns = "id", childColumns = "acc_type_id", onDelete = CASCADE, onUpdate = CASCADE),
        indices = { @Index(name = "Account_PK", value = "id"),
                    @Index(name = "Account_FK", value = "acc_type_id")} )
public class Account {  // Parcelable? Serializable?

    @PrimaryKey(autoGenerate = true)
    private int id;

    private double balance;

    @ColumnInfo(name = "acc_type_id")
    private int accountTypeId;

    // Currency ID zasega lipsva, zashtoto shte dobavi dopulnitelna slojnost

    //////////////////////////

    public Account(double balance, int accountTypeId) {
        this.balance = balance;
        this.accountTypeId = accountTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }
}
