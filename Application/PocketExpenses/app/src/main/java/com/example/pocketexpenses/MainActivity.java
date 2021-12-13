package com.example.pocketexpenses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;

import com.example.pocketexpenses.Repository.AccountTypeRepository;
import com.example.pocketexpenses.Repository.TransactionTypeRepository;
import com.example.pocketexpenses.ViewModel.AccountTypeViewModel;
import com.example.pocketexpenses.ViewModel.TransactionTypeViewModel;
import com.example.pocketexpenses.activities.AccountsActivity;
import com.example.pocketexpenses.activities.BaseActivity;
import com.example.pocketexpenses.dao.TransactionTypeDao;
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
        /* NE IZTRIVAI */ TransactionSubtype dummyToCreateDatabase = new TransactionTypeRepository(getApplication()).getTransactionSubtypeByID(1);


        //////////////////

        binding.btnAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountsActivity.class);
                startActivity(intent);
            }
        });

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