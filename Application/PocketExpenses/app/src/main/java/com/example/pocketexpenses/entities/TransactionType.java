package com.example.pocketexpenses.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transaction_type",
        foreignKeys = @ForeignKey(entity = TransactionDirection.class, parentColumns = "id", childColumns = "tran_dir_id", onDelete = CASCADE, onUpdate = CASCADE),
        indices = { @Index(name = "Tran_Type_PK", value = "id"),
                    @Index(name = "Tran_Type_FK", value = "tran_dir_id")} )
public class TransactionType {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @ColumnInfo(name = "tran_dir_id")
    private int transactionDirectionId;

    ///////////////////////////////////////////


    public TransactionType(String name, int transactionDirectionId) {
        this.name = name;
        this.transactionDirectionId = transactionDirectionId;
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

    public int getTransactionDirectionId() {
        return transactionDirectionId;
    }

    public void setTransactionDirectionId(int transactionDirectionId) {
        this.transactionDirectionId = transactionDirectionId;
    }
}
