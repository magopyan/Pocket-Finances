package com.example.pocketexpenses.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityAccountsInputBinding;
import com.example.pocketexpenses.fragments.AccountInputFragment;
import com.google.android.material.appbar.MaterialToolbar;


public class AccountsInputActivity extends AppCompatActivity {

    private ActivityAccountsInputBinding binding;
    private AccountInputFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsInputBinding.inflate(getLayoutInflater());
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

        FragmentManager manager = getSupportFragmentManager();
        AccountInputFragment defaultFragment;
        if(manager.findFragmentByTag("Input") == null) {
            defaultFragment = AccountInputFragment.newInstance();
            fragment = defaultFragment;
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragmentContainer, defaultFragment, "Input");
            transaction.commit();
        }

    }
}
