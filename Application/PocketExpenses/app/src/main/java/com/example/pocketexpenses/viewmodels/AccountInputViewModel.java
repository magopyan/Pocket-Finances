package com.example.pocketexpenses.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.repositories.AccountInputRepository;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.repositories.TransactionInputRepository;

public class AccountInputViewModel extends ViewModel {

    private MutableLiveData<Account> oAccount;
    private MutableLiveData<AccountType> oAccountType;

    public MutableLiveData<Account> getAccount() {
        if (oAccount == null) {
            oAccount = AccountInputRepository.getInstance().getAccount();
        }
        return oAccount;
    }
    public void setAccount(Account oAccount) {
        AccountInputRepository.getInstance().setAccount(oAccount);
    }

    public MutableLiveData<AccountType> getAccountType() {
        if (oAccountType == null) {
            oAccountType = AccountInputRepository.getInstance().getAccountType();
        }
        return oAccountType;
    }
    public void setAccountType(AccountType oAccountType) {
        AccountInputRepository.getInstance().setAccountType(oAccountType);
    }

    public void reset() {
        oAccount = null;
        oAccountType = null;
        AccountInputRepository.getInstance().reset();
    }
}
