package com.example.pocketexpenses.dao;

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

    @Insert
    long insertAccountType(AccountType accountType);

    @Insert
    long insertAccount(Account account);

    @Update
    void updateAccountType(AccountType accountType);

    @Update
    void updateAccount(Account account);

    @Delete
    void deleteAccountType(AccountType accountType);

    @Delete
    void deleteAccount(Account account);


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // SELECT & SELECT WHERE id
    // for:
    // - AccountType (with Accounts ||| RELATIONSHIP)
    // - Account (SIMPLE ||| NO RELATIONSHIP)   ---> for relationship with Transactions see *TransactionDao*

    @Transaction
    @Query("SELECT * FROM account_type")
    List<AccountTypeWithAccounts> getAllAccountTypes();

    @Transaction
    @Query("SELECT * FROM account_type WHERE id = :id")
    AccountTypeWithAccounts getAccountTypeByID(int id);

    @Transaction
    @Query("SELECT * FROM account")
    List<Account> getAllAccounts();  // N

    @Transaction
    @Query("SELECT * FROM account WHERE id = :id")
    Account getAccountByID(int id);
}
