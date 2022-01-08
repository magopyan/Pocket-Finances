package com.example.pocketexpenses.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityAccountsBinding;
import com.example.pocketexpenses.databinding.ActivityMainBinding;
import com.example.pocketexpenses.databinding.FragmentAccountsListBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.fragments.AccountsListFragment;
import com.example.pocketexpenses.fragments.StatisticsFragment;
import com.example.pocketexpenses.fragments.TransactionsListFragment;
import com.example.pocketexpenses.recyclers.TransactionsAdapter;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccountsActivity extends AppCompatActivity {

    private ActivityAccountsBinding binding;
    private LifecycleOwner owner;
    private List<Transaction> m_oListTransactions;
    private List<Account> m_oListAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //setSupportActionBar(binding.topAppBar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.logo_circle));
        //getSupportActionBar().setTitle("Pocket Finances");

        Intent receivedIntent = getIntent();
        String toastMessage = receivedIntent.getStringExtra("Toast Message");
        if (toastMessage != null)
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

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
                    binding.topAppBar.getMenu().clear();
                    binding.topAppBar.inflateMenu(R.menu.top_app_bar_menu_date);
                    binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_transactions).setChecked(true);
                    break;
                case R.id.bottomnav_accounts:
                    AccountsListFragment accountsFragment = AccountsListFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragmentContainer, accountsFragment, "Accounts").commit();
                    binding.topAppBar.getMenu().clear();
                    binding.topAppBar.inflateMenu(R.menu.top_app_bar_menu);
                    binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_accounts).setChecked(true);
                    break;
                case R.id.bottomnav_statistics:
                    StatisticsFragment statisticsFragment = StatisticsFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragmentContainer, statisticsFragment, "Statistics").commit();
                    binding.topAppBar.getMenu().clear();
                    binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_statistics).setChecked(true);
                    break;
                default:
                    break;
            }
            return true;
        });

        binding.topAppBar.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.top_sort_date:
                        try {
                            sortTransactionListItemsByCurrentDate();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.top_sort:
                        try {
                            sortAccountsListItemsByBalance();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    default:
                }

                return false;
            }
        });
    }

    private void sortTransactionListItemsByCurrentDate() throws ParseException {
        TransactionViewModel oTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        oTransactionViewModel.getAllTransactionsSortByDate().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> oListTransaction) {
                m_oListTransactions = oListTransaction;

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("Transactions");
                if(fragment instanceof TransactionsListFragment){
                    TransactionsListFragment oTransactionsListFragment = (TransactionsListFragment) fragment;
                    oTransactionsListFragment.updateRecyclerViewAdapterItems(m_oListTransactions);
                }
            }
        });

    }

    private void sortAccountsListItemsByBalance() throws ParseException {
        AccountTypeViewModel oAccountTypeViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);

        oAccountTypeViewModel.getAllAccountsSortByBalance().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable final List<Account> oListAccount) {
                m_oListAccounts = oListAccount;

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("Accounts");
                if(fragment instanceof AccountsListFragment){
                    AccountsListFragment oAccountsListFragment = (AccountsListFragment) fragment;
                    oAccountsListFragment.updateRecyclerViewAdapterItems(m_oListAccounts);
                }
            }
        });

    }

}