package com.example.pocketexpenses.dao;

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

    @Insert
    long insertTransaction(Transaction transaction);

    @Update
    void updateTransaction(Transaction transaction);

    @Delete
    void deleteTransaction(Transaction transaction);


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // SELECT & SELECT WHERE id
    // for:
    // - Transaction (SIMPLE ||| NO RELATIONSHIP)
    // - Account (with Transactions ||| RELATIONSHIP)
    // - TransactionSubtypes (with Transactions ||| RELATIONSHIP)


    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction`")
    List<Transaction> getAllTransactions();

    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction` WHERE id = :id")
    Transaction getTransactionByID(int id);

    @androidx.room.Transaction
    @Query("SELECT * FROM account")
    List<AccountWithTransactions> getAllAccounts();

    @androidx.room.Transaction
    @Query("SELECT * FROM account WHERE id = :id")
    AccountWithTransactions getAccountByID(int id);

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_subtype")
    List<TransactionSubtypeWithTransactions> getAllTransactionSubtypes();

    @androidx.room.Transaction
    @Query("SELECT * FROM transaction_subtype WHERE id = :id")
    TransactionSubtypeWithTransactions getTransactionSubtypeByID(int id);

}
