package com.example.pocketexpenses.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.relationships.AccountWithTransactions;
import com.example.pocketexpenses.entities.relationships.TransactionSubtypeWithTransactions;

import java.util.List;

@Dao
public interface TransactionDao { // za AccountWithTransactions i TransactionSubtypeWithTransactions

    @androidx.room.Transaction
    @Insert
    long insertTransaction(Transaction transaction);

    @androidx.room.Transaction
    @Update
    void updateTransaction(Transaction transaction);

    @androidx.room.Transaction
    @Delete
    void deleteTransaction(Transaction transaction);

    @androidx.room.Transaction
    @Query("DELETE FROM `transaction`")
    void deleteAllTransactions();

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // SELECT & SELECT WHERE id
    // for:
    // - Transaction (SIMPLE ||| NO RELATIONSHIP)
    // - Account (with Transactions ||| RELATIONSHIP)
    // - TransactionSubtypes (with Transactions ||| RELATIONSHIP)


    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction`")
    LiveData<List<Transaction>> getAllTransactions();

    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction` WHERE id = :arg0 LIMIT 1")
    LiveData<Transaction> getTransactionByID(int arg0);

    @androidx.room.Transaction
    @Query("SELECT * FROM account")
    LiveData<List<AccountWithTransactions>> getAllAccountsWithTransactions();

    @androidx.room.Transaction
    @Query("SELECT * FROM account WHERE id = :arg0 LIMIT 1")
    LiveData<AccountWithTransactions> getAccountWithTransactionByID(int arg0);

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_subtype")
    LiveData<List<TransactionSubtypeWithTransactions>> getAllTransactionSubtypesWithTransactions();

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_subtype WHERE id = :arg0 LIMIT 1")
    LiveData<TransactionSubtypeWithTransactions> getTransactionSubtypesWithTransactionsByID(int arg0);

}
