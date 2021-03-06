package com.example.pocketexpenses.entities.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;

import java.util.List;

public class AccountTypeWithAccounts {  // One AccountType to many Accounts

    @Embedded
    private AccountType accountType;
    @Relation(
            parentColumn = "id",
            entityColumn = "acc_type_id"
    )
    private List<Account> accountList;  // dali da bude AccountWithTransactions?

    //////////////////////////////////////////


    public AccountTypeWithAccounts(AccountType accountType, List<Account> accountList) {
        this.accountType = accountType;
        this.accountList = accountList;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
}
