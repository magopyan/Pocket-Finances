package com.example.pocketexpenses.dao;


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

    @Insert
    long insertTransactionDirection(TransactionDirection transactionDirection);

    @Insert
    long insertTransactionType(TransactionType transactionType);

    @Insert
    long insertTransactionSubtype(TransactionSubtype transactionSubtype);

    @Update
    void updateTransactionDirection(TransactionDirection transactionDirection);

    @Update
    void updateTransactionType(TransactionType transactionType);

    @Update
    void updateTransactionSubtype(TransactionSubtype transactionSubtype);

    @Delete
    void deleteTransactionDirection(TransactionDirection transactionDirection);

    @Delete
    void deleteTransactionType(TransactionType transactionType);

    @Delete
    void deleteTransactionSubtype(TransactionSubtype transactionSubtype);


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // SELECT & SELECT WHERE id
    // for:
    // - TransactionDirection (with TransactionTypes ||| NESTED RELATIONSHIP (Subtypes inside Type))
    // - TransactionType (with TransactionSubtypes ||| RELATIONSHIP)
    // - TransactionSubtype (SIMPLE ||| NO RELATIONSHIP)   ---> for relationship with Transactions see *TransactionDao*

    @Transaction
    @Query("SELECT * FROM transaction_direction")
    List<TransactionDirectionWithTypesAndSubtypes> getAllDirections();

    @Transaction
    @Query("SELECT * FROM transaction_direction WHERE id = :id")
    List<TransactionDirectionWithTypesAndSubtypes> getDirectionById(int id);

    @Transaction
    @Query("SELECT * FROM transaction_type")
    List<TransactionTypeWithSubtypes> getAllTypes();

    @Transaction
    @Query("SELECT * FROM transaction_type WHERE id = :id")
    List<TransactionTypeWithSubtypes> getTypeById(int id);

    @Transaction
    @Query("SELECT * FROM transaction_subtype")
    List<TransactionSubtype> getAllSubtypes();

    @Transaction
    @Query("SELECT * FROM transaction_subtype WHERE id = :id")
    List<TransactionSubtype> getSubtypeById(int id);
}
