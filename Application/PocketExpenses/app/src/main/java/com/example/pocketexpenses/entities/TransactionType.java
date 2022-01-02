package com.example.pocketexpenses.entities;

import android.media.Image;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.pocketexpenses.entities.relationships.TransactionTypeWithSubtypes;

import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transaction_type",
        foreignKeys = @ForeignKey(entity = TransactionDirection.class, parentColumns = "id", childColumns = "tran_dir_id", onDelete = CASCADE, onUpdate = CASCADE),
        indices = { @Index(name = "Tran_Type_PK", value = "id"),
                    @Index(name = "Tran_Type_FK", value = "tran_dir_id")} )
public class TransactionType {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private Integer ImageId;

    @ColumnInfo(name = "tran_dir_id")
    private int transactionDirectionId;

    @Ignore
    private List<TransactionSubtype> transactionSubtypeList = null;

    ///////////////////////////////////////////

    public TransactionType() {}

    public TransactionType(String name, int transactionDirectionId, Integer ImageId) {
        this.name = name;
        this.transactionDirectionId = transactionDirectionId;
        this.ImageId = ImageId;
    }

    @Ignore
    public TransactionType(String name, int transactionDirectionId, Integer ImageId, List<TransactionSubtype> transactionSubtypeList) {
        super();
        this.name = name;
        this.transactionDirectionId = transactionDirectionId;
        this.ImageId = ImageId;
        this.transactionSubtypeList = transactionSubtypeList;
    }

    public TransactionType(TransactionTypeWithSubtypes transactionTypeWithSubtypes) {
        this.id = transactionTypeWithSubtypes.getTransactionType().getId();
        this.transactionDirectionId = transactionTypeWithSubtypes.getTransactionType().transactionDirectionId;
        this.transactionSubtypeList = transactionTypeWithSubtypes.getSubtypesList();
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

    public Integer getImageId() {
        return ImageId;
    }

    public void setImageId(Integer imageId) {
        ImageId = imageId;
    }

    public List<TransactionSubtype> getTransactionSubtypeList() {
        return transactionSubtypeList;
    }

    public void setTransactionSubtypeList(List<TransactionSubtype> transactionSubtypeList) {
        this.transactionSubtypeList = transactionSubtypeList;
    }
}
