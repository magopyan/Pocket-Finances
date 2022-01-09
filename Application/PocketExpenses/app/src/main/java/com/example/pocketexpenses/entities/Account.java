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

@Entity(tableName = "account",
        foreignKeys = @ForeignKey(entity = AccountType.class, parentColumns = "id", childColumns = "acc_type_id", onDelete = CASCADE, onUpdate = CASCADE),
        indices = { @Index(name = "Account_PK", value = "id"),
                    @Index(name = "Account_FK", value = "acc_type_id")} )
public class Account implements Parcelable {  // Parcelable? Serializable?

    @PrimaryKey(autoGenerate = true)
    private int id;

    private double balance;

    private String name;

    @ColumnInfo(name = "image_id")
    private Integer ImageId;

    @ColumnInfo(name = "acc_type_id")
    private int accountTypeId;

    // Currency ID zasega lipsva, zashtoto shte dobavi dopulnitelna slojnost

    //////////////////////////


    public Account(double balance, String name, int accountTypeId, Integer ImageId) {
        this.balance = balance;
        this.name=name;
        this.accountTypeId = accountTypeId;
        this.ImageId = ImageId;
    }

    protected Account(Parcel in) {
        id = in.readInt();
        balance = in.readDouble();
        name = in.readString();
        accountTypeId = in.readInt();
        if (in.readByte() == 0) {
            ImageId = null;
        } else {
            ImageId = in.readInt();
        }
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getImageId() {
        return ImageId;
    }

    public void setImageId(Integer imageId) {
        ImageId = imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(balance);
        dest.writeString(name);
        dest.writeInt(accountTypeId);
        if (ImageId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ImageId);
        }
    }
}
