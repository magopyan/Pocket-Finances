package com.example.pocketexpenses.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transaction",
        foreignKeys = { @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "account_id", onDelete = CASCADE, onUpdate = CASCADE),
                        @ForeignKey(entity = TransactionSubtype.class, parentColumns = "id", childColumns = "tran_subtype_id", onDelete = CASCADE, onUpdate = CASCADE) },
        indices = { @Index(name = "Transaction_PK", value = "id"),
                    @Index(name = "Transaction_FK1", value = "account_id"),
                    @Index(name = "Transaction_FK2", value = "tran_subtype_id") } )
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;   // = new Date().getTime() ili System.currentTimeMillis()

    private double sum;

    private String note;

    @ColumnInfo(name = "account_id")
    private int accountId;

    @ColumnInfo(name = "tran_subtype_id")
    private int transactionSubtypeId;

    // private int destinationAccountId; ???


    //////////////////////////

    public Transaction(long date, double sum, String note, int accountId, int transactionSubtypeId) {
        this.date = date;
        this.sum = sum;
        this.note = note;
        this.accountId = accountId;
        this.transactionSubtypeId = transactionSubtypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTransactionSubtypeId() {
        return transactionSubtypeId;
    }

    public void setTransactionSubtypeId(int transactionSubtypeId) {
        this.transactionSubtypeId = transactionSubtypeId;
    }
}
