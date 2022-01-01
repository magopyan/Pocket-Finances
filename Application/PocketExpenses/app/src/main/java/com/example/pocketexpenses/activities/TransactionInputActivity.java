package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityTransactionInputBinding;
import com.example.pocketexpenses.fragments.IncomeInputFragment;
import com.example.pocketexpenses.fragments.TransactionInputFragment;
import com.example.pocketexpenses.fragments.TransactionsListFragment;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class TransactionInputActivity extends AppCompatActivity {

    private ActivityTransactionInputBinding binding;
    private TransactionInputViewModel oTransactionInputVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionInputBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oTransactionInputVM = new ViewModelProvider(this).get(TransactionInputViewModel.class);

        Intent receivedIntent = getIntent();


        MaterialToolbar topAppBar = binding.topAppBar;
        String title = receivedIntent.getStringExtra("topBarTitle");
        topAppBar.setTitle(title);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oTransactionInputVM.reset();
                onBackPressed();
            }
        });


        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        oTransactionInputVM.reset();
                        binding.topAppBar.setTitle("Add new Expense");
                        TransactionInputFragment tranInputFragment = TransactionInputFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer, tranInputFragment, "Current Tab").commit();
                        break;
                    case 1:
                        oTransactionInputVM.reset();
                        binding.topAppBar.setTitle("Add new Income");
                        IncomeInputFragment incomeInputFragment = IncomeInputFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer, incomeInputFragment, "Current Tab").commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });


        FragmentManager manager = getSupportFragmentManager();
        TransactionInputFragment defaultFragment;
        if(manager.findFragmentByTag("Input") == null) {
            defaultFragment = TransactionInputFragment.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragmentContainer, defaultFragment, "Input");
            transaction.commit();
        }
    }
}