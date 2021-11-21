package com.example.pocketexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.database.AppDatabase;
import com.example.pocketexpenses.databinding.ActivityMainBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppDatabase oAppDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());  // TRQBVA DA E PREDI setContentView !!!
        View view = binding.getRoot(); // dobaveno
        setContentView(view); // promeneno ot default-a

        createDatabase();

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
    }

    public void addAccountType(View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AccountType accountType = new AccountType(binding.etAccTypeName.getText().toString());
                int id = (int) AppDatabase.getInstance(v.getContext()).accountTypeDao().insertAccountType(accountType);
                accountType.setId(id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.textView2.setText("id:" + accountType.getId() + " name:" + accountType.getName());
                    }
                });
            }
        });

        thread.start();
    }

    public void updateAccountType(View v)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AccountType accountType = new AccountType(binding.etAccTypeName.getText().toString());
                accountType.setId(1);
                oAppDataBase.accountTypeDao().updateAccountType(accountType);
                AccountTypeWithAccounts oAccountTypeWithAccounts = oAppDataBase.accountTypeDao().getAccountTypeByID(1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.textView2.setText("id:" + oAccountTypeWithAccounts.getAccountType().getId() + " name:"
                                + oAccountTypeWithAccounts.getAccountType().getName());
                    }
                });
            }

        });

        thread.start();
    }

    public void deleteAccountType()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AccountTypeWithAccounts oAccountTypeWithAccounts = oAppDataBase.accountTypeDao().getAccountTypeByID(2);
                oAppDataBase.accountTypeDao().deleteAccountType(oAccountTypeWithAccounts.getAccountType());

                oAccountTypeWithAccounts = oAppDataBase.accountTypeDao().getAccountTypeByID(2);
                if(oAccountTypeWithAccounts.getAccountType() == null) // Prilojenieto crash-va tuk, zashtoto e iztrit zapisa
                {                                                     // i metoda getAccountType() vrushta null, po-natatuk shte se opravi
                    runOnUiThread(new Runnable() {                    // tova e za proverka na operaciite samo, toest operaciqta za iztrivane v bazata raboti, vsichko e nared
                        @Override
                        public void run() {
                            binding.textView2.setText("Record is deleted");
                        }
                    });
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.textView2.setText("Record is not deleted");
                        }
                    });
                }
            }
        });

        thread.start();
    }

    public void queryAccount()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AccountTypeWithAccounts oAccountTypeWithAccounts = oAppDataBase.accountTypeDao().getAccountTypeByID(1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.textView2.setText("ID: " + oAccountTypeWithAccounts.getAccountType().getId() + " Name: "+
                                oAccountTypeWithAccounts.getAccountType().getName());
                    }
                });
            }
        });

        thread.start();
    }

    public void createDatabase()
    {
        oAppDataBase = AppDatabase.getInstance(getApplicationContext());
    }
}