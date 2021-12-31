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
import com.example.pocketexpenses.fragments.StatisticsFragment;
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
        transaction.replace(R.id.fragmentContainer, defaultFragment, "Transactions");
        transaction.commit();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
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
                                replace(R.id.fragmentContainer, accountsFragment, "Accounts").commit();
                        binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_accounts).setChecked(true);
                    break;
                case R.id.bottomnav_statistics:
                    StatisticsFragment statisticsFragment = StatisticsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragmentContainer, statisticsFragment, "Statistics").commit();
                    binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_accounts).setChecked(true);
                        binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_statistics).setChecked(true);
                    break;
                default:
                    break;
            }
            return true;
        });

        binding.topAppBar.setNavigationOnClickListener(menuItem -> {
            switch(menuItem.getId()) {
                case R.id.top_search:
                    break;
                case R.id.top_sort:
                    break;
            }
        });
    }
}