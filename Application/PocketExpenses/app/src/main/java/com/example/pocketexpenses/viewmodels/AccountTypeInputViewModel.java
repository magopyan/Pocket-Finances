package com.example.pocketexpenses.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pocketexpenses.Repository.AccountTypeInputRepository;
import com.example.pocketexpenses.entities.AccountType;

public class AccountTypeInputViewModel extends ViewModel {

    private MutableLiveData<AccountType> oAccountType;

    public MutableLiveData<AccountType> getAccountType() {
        if (oAccountType == null) {
            oAccountType = AccountTypeInputRepository.getInstance().getAccountType();
        }
        return oAccountType;
    }
    public void setAccountType(AccountType oAccountType) {
        AccountTypeInputRepository.getInstance().setAccountType(oAccountType);
    }
}
