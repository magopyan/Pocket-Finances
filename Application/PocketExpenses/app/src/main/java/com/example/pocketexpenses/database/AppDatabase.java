package com.example.pocketexpenses.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pocketexpenses.dao.AccountTypeDao;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;


@Database(entities = {Account.class, AccountType.class, }, version = 1, exportSchema = false) // ima li smisul exportSchema da e true?
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;
    private static final String DB_NAME = "Pocket_Expenses_Database";

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();  // dobavi opcii?
        }
        return instance;
    }

    public abstract AccountDao accountDao();
    public abstract AccountTypeDao accountTypeDao();
}
