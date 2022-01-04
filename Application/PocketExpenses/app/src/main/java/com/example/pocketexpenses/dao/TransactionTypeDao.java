package com.example.pocketexpenses.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.entities.relationships.TransactionTypeWithSubtypes;

import java.util.List;

@Dao
public interface TransactionTypeDao { // Za TransactionDirectionWithTypesAndSubtypes (vkluchitelno TransactionTypeWithSubtypes)

    @Transaction
    @Insert
    long insertTransactionDirection(TransactionDirection transactionDirection);

    @Transaction
    @Insert
    long insertTransactionType(TransactionType transactionType);

    @Transaction
    @Insert
    long insertTransactionSubtype(TransactionSubtype transactionSubtype);

    @Transaction
    @Update
    void updateTransactionDirection(TransactionDirection transactionDirection);

    @Transaction
    @Update
    void updateTransactionType(TransactionType transactionType);

    @Transaction
    @Update
    void updateTransactionSubtype(TransactionSubtype transactionSubtype);

    @Transaction
    @Delete
    void deleteTransactionDirection(TransactionDirection transactionDirection);

    @Transaction
    @Delete
    void deleteTransactionType(TransactionType transactionType);

    @Transaction
    @Delete
    void deleteTransactionSubtype(TransactionSubtype transactionSubtype);

    @Transaction
    @Query("DELETE FROM transaction_direction")
    void deleteAllTransactionDirections();

    @Transaction
    @Query("DELETE FROM transaction_type")
    void deleteAllTransactionTypes();

    @Transaction
    @Query("DELETE FROM transaction_subtype")
    void deleteAllTransactionSubtypes();

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // SELECT & SELECT WHERE id
    // for:
    // - TransactionDirection (with TransactionTypes ||| NESTED RELATIONSHIP (Subtypes inside Type))
    // - TransactionType (with TransactionSubtypes ||| RELATIONSHIP)
    // - TransactionSubtype (SIMPLE ||| NO RELATIONSHIP)   ---> for relationship with Transactions see *TransactionDao*

    @Transaction
    @Query("SELECT * FROM transaction_direction")
    LiveData<List<TransactionDirectionWithTypesAndSubtypes>> getAllTransactionDirectionWithTypesAndSubtypes();

    @Transaction
    @Query("SELECT * FROM transaction_direction")
    LiveData<List<TransactionDirection>> getAllTransactionDirections();

    @Transaction
    @Query("SELECT * FROM transaction_direction WHERE id = :arg0 LIMIT 1")
    LiveData<TransactionDirectionWithTypesAndSubtypes> getDirectionWithTypesAndSubtypesById(int arg0);

    @Transaction
    @Query("SELECT * FROM transaction_direction WHERE id = :arg0 LIMIT 1")
    LiveData<TransactionDirection> getDirectionById(int arg0);

    @Transaction
    @Query("SELECT * FROM transaction_type")
    LiveData<List<TransactionTypeWithSubtypes>> getAllTransactionTypeWithSubtypes();

    @Transaction
    @Query("SELECT * FROM transaction_type")
    LiveData<List<TransactionType>> getAllTransactionTypes();

    @Transaction
    @Query("SELECT * FROM transaction_type WHERE id = :arg0 LIMIT 1")
    LiveData<TransactionTypeWithSubtypes> getTypeWithSubtypesById(int arg0);

    @Transaction
    @Query("SELECT * FROM transaction_type WHERE id = :arg0 LIMIT 1")
    LiveData<TransactionType> getTypeById(int arg0);

    @Transaction
    @Query("SELECT * FROM transaction_subtype")
    LiveData<List<TransactionSubtype>> getAllTransactionSubtype();

    @Transaction
    @Query("SELECT * FROM transaction_subtype WHERE id = :arg0 LIMIT 1")
    LiveData<TransactionSubtype> getSubtypeById(int arg0);
}
