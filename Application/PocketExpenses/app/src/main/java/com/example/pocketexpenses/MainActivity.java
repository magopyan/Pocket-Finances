package com.example.pocketexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.database.AppDatabase;
import com.example.pocketexpenses.databinding.ActivityMainBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());  // TRQBVA DA E PREDI setContentView !!!
        View view = binding.getRoot(); // dobaveno
        setContentView(view); // promeneno ot default-a

        binding.bAddAccType.setOnClickListener(this::addAccType);

        binding.bAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public void addAccType(View v) {
        AccountType accountType = new AccountType(binding.etAccTypeName.getText().toString());
        int id = (int) AppDatabase.getInstance(this).accountTypeDao().insertAccountType(accountType);
        accountType.setId(id);
        binding.etAccTypeName.setText("id:" + accountType.getId() + " name:" + accountType.getName());
    }

    @Override
    public void onClick(View v) {

    }
}