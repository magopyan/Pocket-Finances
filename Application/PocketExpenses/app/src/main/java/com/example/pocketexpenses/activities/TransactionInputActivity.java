package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityTransactionInputBinding;
import com.example.pocketexpenses.fragments.TransactionInputFragment;
import com.example.pocketexpenses.fragments.TransactionsListFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class TransactionInputActivity extends AppCompatActivity {

    private ActivityTransactionInputBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionInputBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent receivedIntent = getIntent();

        MaterialToolbar topAppBar = binding.topAppBar;
        String title = receivedIntent.getStringExtra("topBarTitle");
        topAppBar.setTitle(title);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        binding.topAppBar.setTitle("Add new Expense");
                        TransactionInputFragment tranInputFragment = TransactionInputFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragmentContainer, tranInputFragment, "Transactions").commit();
                        break;
                    case 1:
                        binding.topAppBar.setTitle("Add new Income");
                        //
                        //
                        //
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        FragmentManager manager = getSupportFragmentManager();
        if(manager.findFragmentByTag("Input") == null) {
            TransactionInputFragment defaultFragment = TransactionInputFragment.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragmentContainer, defaultFragment, "Input");
            transaction.commit();
        }
    }
}