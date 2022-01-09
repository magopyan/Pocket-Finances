package com.example.pocketexpenses.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "transaction_subtype",
        foreignKeys = @ForeignKey(entity = TransactionType.class, parentColumns = "id", childColumns = "tran_type_id", onDelete = CASCADE, onUpdate = CASCADE),
        indices = { @Index(name = "Tran_Subtype_PK", value = "id"),
                    @Index(name = "Tran_Subtype_FK", value = "tran_type_id")} )
public class TransactionSubtype {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @ColumnInfo(name = "image_id")
    private Integer ImageId;

    @ColumnInfo(name = "tran_type_id")
    private int transactionTypeId;


    //////////////////////////

    public TransactionSubtype() {}

    @Ignore
    public TransactionSubtype(String name, int transactionTypeId, Integer ImageId) {
        this.name = name;
        this.transactionTypeId = transactionTypeId;
        this.ImageId = ImageId;
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

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Integer getImageId() {
        return ImageId;
    }

    public void setImageId(Integer imageId) {
        ImageId = imageId;
    }
}
