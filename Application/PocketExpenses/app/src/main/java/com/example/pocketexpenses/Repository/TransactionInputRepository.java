package com.example.pocketexpenses.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;

public class TransactionInputRepository {

    // Singleton code
    ///////////////////////////////////////////////////////////
    private static TransactionInputRepository instance;

    private TransactionInputRepository() {}

    public static TransactionInputRepository getInstance() {
        if(instance == null) {
            instance = new TransactionInputRepository();
        }
        return instance;
    }

    // Repository code
    ///////////////////////////////////////////////////////////
    private MutableLiveData<Transaction> oTransaction;
    private MutableLiveData<Account> oAccount;
    private MutableLiveData<TransactionType> oTransactionType;
    private MutableLiveData<TransactionSubtype> oTransactionSubtype;


    public MutableLiveData<Transaction> getTransaction() {
        if(oTransaction == null) {
            oTransaction = new MutableLiveData<>();
        }
        return oTransaction;
    }
    public void setTransaction(Transaction transaction) {
        oTransaction.setValue(transaction);
    }

    public MutableLiveData<Account> getAccount() {
        if (oAccount == null) {
            oAccount = new MutableLiveData<>();
        }
        return oAccount;
    }
    public void setAccount(Account account) {
        oAccount.setValue(account);
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

    public void reset() {
        oTransaction = null;
        oAccount = null;
        oTransactionType = null;
        oTransactionSubtype = null;
    }
}

