package com.example.pocketexpenses.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityAccountsBinding;
import com.example.pocketexpenses.databinding.ActivityMainBinding;
import com.example.pocketexpenses.databinding.FragmentAccountsListBinding;
import com.example.pocketexpenses.fragments.AccountsListFragment;
import com.example.pocketexpenses.fragments.TransactionsListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class AccountsActivity extends AppCompatActivity {

    private ActivityAccountsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FragmentManager manager = getSupportFragmentManager();
        TransactionsListFragment defaultFragment = TransactionsListFragment.newInstance();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainer, defaultFragment, "Transactions");
        transaction.commit();

//        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.bottomnav_transactions:
//                    TransactionsListFragment transactionsFragment = TransactionsListFragment.newInstance();
//                    transaction.replace(R.id.fragmentContainer, transactionsFragment, "Transactions");
//                    transaction.commit();
//                    break;
//                case R.id.bottomnav_accounts:
//                    AccountsListFragment accountsFragment = AccountsListFragment.newInstance();
//                    transaction.replace(R.id.fragmentContainer, accountsFragment, "Accounts");
//                    transaction.commit();
//                    break;
//                case R.id.bottomnav_statistics:
//                    // navigate to StatisticsFragment
//                    break;
//            }
//            return true;
//        });

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomnav_transactions:
                        TransactionsListFragment transactionsFragment = TransactionsListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer, transactionsFragment, "Transactions").commit();
                        binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_transactions).setChecked(true);
                        break;
                    case R.id.bottomnav_accounts:
                        AccountsListFragment accountsFragment = AccountsListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer, accountsFragment, "Transactions").commit();
                        binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_accounts).setChecked(true);
                        break;
                    case R.id.bottomnav_statistics:
                        // navigate to StatisticsFragment
                        binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_statistics).setChecked(true);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}