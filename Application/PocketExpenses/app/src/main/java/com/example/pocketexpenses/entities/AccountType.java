package com.example.pocketexpenses.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;

import java.util.List;

@Entity(tableName = "account_type")
public class AccountType {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @ColumnInfo(name = "image_id")
    private Integer ImageId;

    @Ignore
    private List<Account> oAccountsList = null;

    //////////////////////////

    public AccountType(String name, Integer ImageId) {
        this.name = name;
        this.ImageId = ImageId;
    }

    public AccountType(AccountTypeWithAccounts oAccountTypeWithAccounts) {
        this.id = oAccountTypeWithAccounts.getAccountType().getId();
        this.name = oAccountTypeWithAccounts.getAccountType().getName();
        this.oAccountsList = oAccountTypeWithAccounts.getAccountList();
        this.ImageId = oAccountTypeWithAccounts.getAccountType().getImageId();
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

    public Integer getImageId() {
        return ImageId;
    }

    public void setImageId(Integer imageId) {
        ImageId = imageId;
    }
}
