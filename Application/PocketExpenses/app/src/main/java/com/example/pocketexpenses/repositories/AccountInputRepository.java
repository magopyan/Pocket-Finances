package com.example.pocketexpenses.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;

public class AccountInputRepository {

    // Singleton code
    ///////////////////////////////////////////////////////////
    private static AccountInputRepository instance = null;

    private AccountInputRepository() {}

    public static AccountInputRepository getInstance() {
        if(instance == null) {
            instance = new AccountInputRepository();
        }
        return instance;
    }

    // Repository code
    ///////////////////////////////////////////////////////////
    private MutableLiveData<Account> oAccount;
    private MutableLiveData<AccountType> oAccountType;

    public MutableLiveData<Account> getAccount() {
        if (oAccount == null) {
            oAccount = new MutableLiveData<>();
        }
        return oAccount;
    }
    public void setAccount(Account oAccountArg) {
        if(oAccount == null) {
            oAccount = new MutableLiveData<>();
        }
        oAccount.setValue(oAccountArg);
    }

    public MutableLiveData<AccountType> getAccountType() {
        if (oAccountType == null) {
            oAccountType = new MutableLiveData<>();
        }
        return oAccountType;
    }
    public void setAccountType(AccountType oAccountTypeArg) {
        if(oAccountType == null) {
            oAccountType = new MutableLiveData<>();
        }
        oAccountType.setValue(oAccountTypeArg);
    }

    public void reset() {
        oAccount = null;
        oAccountType = null;
    }
}
