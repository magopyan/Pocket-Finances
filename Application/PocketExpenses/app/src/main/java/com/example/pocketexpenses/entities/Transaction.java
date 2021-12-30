package com.example.pocketexpenses.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transaction",
        foreignKeys = { @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "account_id", onDelete = CASCADE, onUpdate = CASCADE),
                        @ForeignKey(entity = TransactionSubtype.class, parentColumns = "id", childColumns = "tran_subtype_id", onDelete = CASCADE, onUpdate = CASCADE) },
        indices = { @Index(name = "Transaction_PK", value = "id"),
                    @Index(name = "Transaction_FK1", value = "account_id"),
                    @Index(name = "Transaction_FK2", value = "tran_subtype_id") } )
public class Transaction implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;

    private double sum;

    private String note;

    @ColumnInfo(name = "account_id")
    private int accountId;

    @ColumnInfo(name = "tran_subtype_id")
    private int transactionSubtypeId;

    // private int destinationAccountId; ???


    //////////////////////////

    public Transaction(String date, double sum, String note, int accountId, int transactionSubtypeId) {
        this.date = date;
        this.sum = sum;
        this.note = note;
        this.accountId = accountId;
        this.transactionSubtypeId = transactionSubtypeId;
    }

    protected Transaction(Parcel in) {
        id = in.readInt();
        date = in.readString();
        sum = in.readDouble();
        note = in.readString();
        accountId = in.readInt();
        transactionSubtypeId = in.readInt();
    }

    @Ignore
    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeDouble(sum);
        dest.writeString(note);
        dest.writeInt(accountId);
        dest.writeInt(transactionSubtypeId);
    }
}
