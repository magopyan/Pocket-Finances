package com.example.pocketexpenses.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;

import java.util.List;


@Dao
public interface AccountTypeDao {  // za AccountTypeWithAccounts

    @Transaction
    @Insert
    long insertAccountType(AccountType accountType);

    @Transaction
    @Insert
    long insertAccount(Account account);

    @Transaction
    @Update
    void updateAccountType(AccountType accountType);

    @Transaction
    @Update
    void updateAccount(Account account);

    @Transaction
    @Delete
    void deleteAccountType(AccountType accountType);

    @Transaction
    @Delete
    void deleteAccount(Account account);

    @Transaction
    @Query("DELETE FROM account_type")
    void deleteAllAccountTypes();

    @Transaction
    @Query("DELETE FROM account")
    void deleteAllAccounts();

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // SELECT & SELECT WHERE id
    // for:
    // - AccountType (with Accounts ||| RELATIONSHIP)
    // - Account (SIMPLE ||| NO RELATIONSHIP)   ---> for relationship with Transactions see *TransactionDao*

    @Transaction
    @Query("SELECT * FROM account_type")
    LiveData<List<AccountType>> getAllAccountTypes();

    @Transaction
    @Query("SELECT * FROM account_type WHERE id = :id")
    AccountType getAccountTypeByID(int id);

    @Transaction
    @Query("SELECT * FROM account")
    LiveData<List<Account>> getAllAccounts();  // N

    @Transaction
    @Query("SELECT * FROM account WHERE id = :id")
    Account getAccountByID(int id);
}
