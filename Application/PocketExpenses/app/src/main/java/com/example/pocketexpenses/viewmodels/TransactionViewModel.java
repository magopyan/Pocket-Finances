package com.example.pocketexpenses.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pocketexpenses.repositories.TransactionRepository;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.relationships.AccountWithTransactions;
import com.example.pocketexpenses.entities.relationships.TransactionSubtypeWithTransactions;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository oTransactionRepository;
    private LiveData<List<Transaction>> oLiveDataListAllTransactions;
    private LiveData<List<AccountWithTransactions>> oLiveDataListAllAccountsWithTransactions;
    private LiveData<List<TransactionSubtypeWithTransactions>> oLiveDataListAllTransactionSubtypesWithTransactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        oTransactionRepository = new TransactionRepository(application);
        oLiveDataListAllTransactions = oTransactionRepository.getAllTransactions();
        oLiveDataListAllAccountsWithTransactions = oTransactionRepository.getAllAccountsWithTransactions();
        oLiveDataListAllTransactionSubtypesWithTransactions = oTransactionRepository.getAllTransactionSubtypesWithTransactions();
    }

    public Transaction getTransactionByID(int nID){
        return oTransactionRepository.getTransactionByID(nID);
    }

    public int insertTransaction(Transaction oTransaction){
        return oTransactionRepository.insertTransaction(oTransaction);
    }

    public void updateTransaction(Transaction oTransaction){
        oTransactionRepository.updateTransaction(oTransaction);
    }

    public void deleteTransaction(Transaction oTransaction){
        oTransactionRepository.deleteTransaction(oTransaction);
    }

    public void deleteAllTransactions(){
        oTransactionRepository.deleteAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions(){
        return oLiveDataListAllTransactions;
    }

    public LiveData<List<AccountWithTransactions>> getAllAccountsWithTransactions(){
        return oLiveDataListAllAccountsWithTransactions;
    }

    public LiveData<List<TransactionSubtypeWithTransactions>> getAllTransactionSubtypesWithTransactions(){
        return oLiveDataListAllTransactionSubtypesWithTransactions;
    }

    public AccountWithTransactions getAccountWithTransactionByID(int nID){
        return oTransactionRepository.getAccountWithTransactionByID(nID);
    }

    public TransactionSubtypeWithTransactions getTransactionSubtypeWithTransactionsByID(int nID){
        return oTransactionRepository.getTransactionSubtypeWithTransactionsByID(nID);
    }
}
