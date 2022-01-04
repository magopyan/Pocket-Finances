package com.example.pocketexpenses.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pocketexpenses.repositories.AccountTypeRepository;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;

import java.util.List;

public class AccountTypeViewModel extends AndroidViewModel {

    private AccountTypeRepository oAccountTypeRepository;
    private LiveData<List<Account>> oLiveDataListAllAccounts;
    private LiveData<List<AccountType>> oLiveDataListAllAccountTypes;
    private LiveData<List<AccountTypeWithAccounts>> oLiveDataListAccountTypesWithAccounts;

    public AccountTypeViewModel(@NonNull Application application) {
        super(application);
        oAccountTypeRepository = new AccountTypeRepository(application);
        oLiveDataListAllAccounts = oAccountTypeRepository.getAllAccounts();
        oLiveDataListAllAccountTypes = oAccountTypeRepository.getAllAccountTypes();
        oLiveDataListAccountTypesWithAccounts = oAccountTypeRepository.getAllAccountTypesWithAccounts();
    }

    public LiveData<AccountType> getAccountTypeByID(int nID){
        return oAccountTypeRepository.getAccountTypeByID(nID);
    }

    public int insertAccountType(AccountType oAccountType){
        return oAccountTypeRepository.insertAccountType(oAccountType);
    }

    public void updateAccountType(AccountType oAccountType){
        oAccountTypeRepository.updateAccountType(oAccountType);
    }

    public void deleteAccountType(AccountType oAccountType){
        oAccountTypeRepository.deleteAccountType(oAccountType);
    }

    public void deleteAllAccountTypes(){
        oAccountTypeRepository.deleteAllAccountTypes();
    }

    public LiveData<List<AccountType>> getAllAccountTypes(){
        return oLiveDataListAllAccountTypes;
    }

    public LiveData<Account> getAccountByID(int nID){
        return oAccountTypeRepository.getAccountByID(nID);
    }

    public int insertAccount(Account oAccount){
        return oAccountTypeRepository.insertAccount(oAccount);
    }

    public void updateAccount(Account oAccount){
        oAccountTypeRepository.updateAccount(oAccount);
    }

    public void deleteAccount(Account oAccount){
        oAccountTypeRepository.deleteAccount(oAccount);
    }

    public void deleteAllAccounts(){
        oAccountTypeRepository.deleteAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts(){
        return oLiveDataListAllAccounts;
    }

    public LiveData<List<AccountTypeWithAccounts>> getAllAccountTypesWithAccounts(){
        return oLiveDataListAccountTypesWithAccounts;
    }

    public LiveData<AccountTypeWithAccounts> getAccountTypeWithAccountsByID(int nID){
        return oAccountTypeRepository.getAccountTypeWithAccountsById(nID);
    }
}
