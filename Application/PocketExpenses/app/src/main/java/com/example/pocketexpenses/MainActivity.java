package com.example.pocketexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.repositories.AccountTypeRepository;
import com.example.pocketexpenses.repositories.TransactionRepository;
import com.example.pocketexpenses.repositories.TransactionTypeRepository;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionViewModel;
import com.example.pocketexpenses.activities.AccountsActivity;
import com.example.pocketexpenses.activities.BaseActivity;
import com.example.pocketexpenses.activities.TransactionsActivity;
import com.example.pocketexpenses.database.AppDatabase;
import com.example.pocketexpenses.databinding.ActivityMainBinding;
import com.example.pocketexpenses.entities.TransactionSubtype;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private AppDatabase oAppDataBase;
    private AccountTypeRepository m_oAccountTypeRepository;
    private AccountTypeViewModel m_oAccountTypeViewModel;
    private TransactionRepository m_oTransactionRepository;
    private TransactionViewModel m_oTransactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());  // TRQBVA DA E PREDI setContentView !!!
        View view = binding.getRoot(); // dobaveno
        setContentView(view); // promeneno ot default-a

        createDatabase();
        /* NE IZTRIVAI */ TransactionSubtype dummyToCreateDatabase = new TransactionTypeRepository(getApplication()).getTransactionSubtypeByID(1);


        //////////////////

        binding.btnTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                startActivity(intent);
            }
        });

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