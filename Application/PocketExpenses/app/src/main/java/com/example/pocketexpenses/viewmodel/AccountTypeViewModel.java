package com.example.pocketexpenses.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;

import java.util.List;

public class AccountTypeViewModel extends ViewModel {

    private MutableLiveData<List<AccountTypeWithAccounts>> accountTypes;

    public LiveData<List<AccountTypeWithAccounts>> getAccountTypes() {
        if (accountTypes == null) {
            accountTypes = new MutableLiveData<List<AccountTypeWithAccounts>>();
            loadUsers();
        }
        return accountTypes;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }


}
