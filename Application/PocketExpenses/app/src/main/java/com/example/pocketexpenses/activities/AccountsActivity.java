package com.example.pocketexpenses.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityAccountsBinding;
import com.example.pocketexpenses.databinding.ActivityMainBinding;
import com.example.pocketexpenses.databinding.FragmentAccountsListBinding;
import com.example.pocketexpenses.entities.Transaction;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent receivedIntent = getIntent();
        String toastMessage = receivedIntent.getStringExtra("Toast Message");
        if(toastMessage != null)
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
                                replace(R.id.fragmentContainer, transactionsFragment, "Fragment").commit();
                        binding.topAppBar.getMenu().clear();
                        binding.topAppBar.inflateMenu(R.menu.top_app_bar_menu_date);
                        binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_transactions).setChecked(true);
                        break;
                case R.id.bottomnav_accounts:
                    AccountsListFragment accountsFragment = AccountsListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer, accountsFragment, "Fragment").commit();
                        binding.topAppBar.getMenu().clear();
                        binding.topAppBar.inflateMenu(R.menu.top_app_bar_menu);
                        binding.bottomNavigationView.getMenu().findItem(R.id.bottomnav_accounts).setChecked(true);
                    break;
                case R.id.bottomnav_statistics:
                        StatisticsFragment statisticsFragment = StatisticsFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer, statisticsFragment, "Fragement").commit();
                        binding.topAppBar.getMenu().clear();
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
                    try {
                        sortTransactionListItemsByCurrentDate();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
    }

    private void sortTransactionListItemsByCurrentDate() throws ParseException {
        TransactionViewModel oTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        // GetValue() vrushta LiveData. nqma kak da se cast-ne kum List<Transaction>.ili trqbva observe(), ili da napravish ot Dao nagore
        // edin metod getAllTransactionsSortDate kudeto e select * from Transactions order by date asc/desc
        List<Transaction> oListTransactions = oTransactionViewModel.getAllTransactions().getValue();

        List<Transaction> oListTransactionsLesserThanCurrentDate = new ArrayList<>();
        List<Transaction> oListTransactionsEqualToCurrentDate = new ArrayList<>();
        List<Transaction> oListTransactionsGreaterThanCurrentDate = new ArrayList<>();

        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(currentDate);


        for(int i = 0;i<oListTransactions.size();i++)
        {
            Transaction oTransaction = oListTransactions.get(i);

            Date d1 = df.parse(oTransaction.getDate());
            Date CurrentDate = df.parse(formattedDate);

            if(d1.compareTo(CurrentDate) == -1)
            {
                oListTransactionsLesserThanCurrentDate.add(oListTransactions.get(i));
            }
            else if(d1.compareTo(CurrentDate) == 0)
            {
                oListTransactionsEqualToCurrentDate.add(oListTransactions.get(i));
            }
            else if(d1.compareTo(CurrentDate) == 1)
            {
                oListTransactionsGreaterThanCurrentDate.add(oListTransactions.get(i));
            }
        }

        Collections.sort(oListTransactionsLesserThanCurrentDate, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {

                Date d1 = null;
                Date d2 = null;

                try {
                    d1 = df.parse(o1.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    d2 = df.parse(o2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                return d1.compareTo(d2);
            }
        });

        Collections.sort(oListTransactionsGreaterThanCurrentDate, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {

                Date d1 = null;
                Date d2 = null;

                try {
                    d1 = df.parse(o1.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    d2 = df.parse(o2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                return d1.compareTo(d2);
            }
        });

        List<Transaction> newData = oListTransactionsEqualToCurrentDate;

        newData.addAll(oListTransactionsLesserThanCurrentDate);
        newData.addAll(0, oListTransactionsGreaterThanCurrentDate);

        TransactionsAdapter adapter = new TransactionsAdapter();
        adapter.setTransactionsData(newData);

    }
}