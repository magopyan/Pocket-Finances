package com.example.pocketexpenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.Repository.AccountTypeRepository;
import com.example.pocketexpenses.Repository.TransactionTypeRepository;
import com.example.pocketexpenses.ViewModel.AccountTypeViewModel;
import com.example.pocketexpenses.ViewModel.TransactionTypeViewModel;
import com.example.pocketexpenses.activities.BaseActivity;
import com.example.pocketexpenses.database.AppDatabase;
import com.example.pocketexpenses.databinding.ActivityMainBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.entities.relationships.TransactionTypeWithSubtypes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private AppDatabase oAppDataBase;
    private AccountTypeRepository m_oAccountTypeRepository;
    private AccountTypeViewModel m_oAccountTypeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());  // TRQBVA DA E PREDI setContentView !!!
        View view = binding.getRoot(); // dobaveno
        setContentView(view); // promeneno ot default-a

        createDatabase();
        m_oAccountTypeRepository = new AccountTypeRepository(getApplication());


        //////////////////

        TransactionTypeRepository oTranTypeR = new TransactionTypeRepository(getApplication());

//        TransactionDirection direction1 = new TransactionDirection("Income", 1);
//        TransactionDirection direction2 = new TransactionDirection("Expense", -1);
//        int iDir1Id = oTranTypeR.insertTransactionDirection(direction1);
//        int iDir2Id = oTranTypeR.insertTransactionDirection(direction2);
////
////
//        TransactionType tranType1 = new TransactionType("Vehicle", 2);
//        oTranTypeR.insertTransactionType(tranType1);
//        TransactionSubtype tranSubtype1 = new TransactionSubtype("Fuel", 1);
//        TransactionSubtype tranSubtype2 = new TransactionSubtype("Repairs", 1);
//        oTranTypeR.insertTransactionSubtype(tranSubtype1);
//        oTranTypeR.insertTransactionSubtype(tranSubtype2);

        TransactionSubtype tranSubtype3 = new TransactionSubtype("Fines", 1);
        int newId = oTranTypeR.insertTransactionSubtype(tranSubtype3);
        int i = 0;



        /////////////////

        binding.btnAddAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccountType(v);
            }
        });

        binding.btnUpdateAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccountType(v);
            }
        });

        binding.btnDeleteAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountType();
            }
        });

        binding.btnQueryAccountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryAccount();
            }
        });

        binding.btnStartApp.setOnClickListener(this);
    }
    
    public void addAccountType(View v) {

    }

    public void updateAccountType(View v)
    {

    }

    public void deleteAccountType()
    {

    }

    public void queryAccount()
    {

    }

    public void createDatabase()
    {
        oAppDataBase = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
    }
}