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
//        if (oAccount == null) {
//            oAccount = TransactionInputRepository.getInstance().getAccount();
//        }
//        return oAccount;
        return TransactionInputRepository.getInstance().getAccount();
    }
    public void setAccount(Account account) {
        TransactionInputRepository.getInstance().setAccount(account);
    }

    public MutableLiveData<TransactionType> getTransactionType() {
        if (oTransactionType == null) {
            oTransactionType = new MutableLiveData<>();
        }
        return oTransactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        oTransactionType.setValue(transactionType);
    }

    public MutableLiveData<TransactionSubtype> getTransactionSubtype() {
        if(oTransactionSubtype == null) {
            oTransactionSubtype = new MutableLiveData<>();
        }
        return oTransactionSubtype;
    }
    public void setTransactionSubtype(TransactionSubtype transactionSubtype) {
        oTransactionSubtype.setValue(transactionSubtype);
    }

    private void reset() {
        TransactionInputRepository.getInstance().reset();
    }
}


