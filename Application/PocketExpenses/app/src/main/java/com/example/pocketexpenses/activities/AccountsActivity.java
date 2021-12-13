package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityAccountsBinding;
import com.example.pocketexpenses.databinding.ActivityMainBinding;
import com.example.pocketexpenses.fragments.AccountsListFragment;

public class AccountsActivity extends AppCompatActivity {

    private ActivityAccountsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FragmentManager manager = getSupportFragmentManager();
        AccountsListFragment fragment = AccountsListFragment.newInstance();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainer, fragment, "Accounts");
        transaction.commit();
    }
}