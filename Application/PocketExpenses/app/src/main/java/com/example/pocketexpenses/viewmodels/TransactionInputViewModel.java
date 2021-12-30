package com.example.pocketexpenses.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pocketexpenses.Repository.TransactionInputRepository;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;


public class TransactionInputViewModel extends ViewModel {

    private MutableLiveData<Transaction> oTransaction;
    private MutableLiveData<Account> oAccount;
    private MutableLiveData<TransactionType> oTransactionType;
    private MutableLiveData<TransactionSubtype> oTransactionSubtype;


    public MutableLiveData<Transaction> getTransaction() {
        if(oTransaction == null) {
            oTransaction = TransactionInputRepository.getInstance().getTransaction();
        }
        return oTransaction;
    }
    public void setTransaction(Transaction transaction) {
        TransactionInputRepository.getInstance().setTransaction(transaction);
    }

    public MutableLiveData<Account> getAccount() {
        if (oAccount == null) {
            oAccount = TransactionInputRepository.getInstance().getAccount();
        }
        return oAccount;
    }
    public void setAccount(Account account) {
        TransactionInputRepository.getInstance().setAccount(account);
    }

    public MutableLiveData<TransactionType> getTransactionType() {
        if (oTransactionType == null) {
            oTransactionType = TransactionInputRepository.getInstance().getTransactionType();
        }
        return oTransactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        TransactionInputRepository.getInstance().setTransactionType(transactionType);
    }

    public MutableLiveData<TransactionSubtype> getTransactionSubtype() {
        if(oTransactionSubtype == null) {
            oTransactionSubtype = TransactionInputRepository.getInstance().getTransactionSubtype();
        }
        return oTransactionSubtype;
    }
    public void setTransactionSubtype(TransactionSubtype transactionSubtype) {
        TransactionInputRepository.getInstance().setTransactionSubtype(transactionSubtype);
    }

    public void reset() {
        oTransaction = null;
        oAccount = null;
        oTransactionType = null;
        oTransactionSubtype = null;
        TransactionInputRepository.getInstance().reset();
    }
}


