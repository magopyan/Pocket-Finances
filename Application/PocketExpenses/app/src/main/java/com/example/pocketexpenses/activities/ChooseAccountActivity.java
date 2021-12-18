package com.example.pocketexpenses.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityChooseAccountBinding;
import com.example.pocketexpenses.databinding.ActivityTransactionInputBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.fragments.AccountsListFragment;
import com.example.pocketexpenses.fragments.ChooseAccountFragment;
import com.example.pocketexpenses.fragments.TransactionsListFragment;
import com.example.pocketexpenses.onclicklisteners.OnAccountClickListener;
import com.example.pocketexpenses.recyclers.AccountsAdapter;
import com.example.pocketexpenses.recyclers.ChooseAccountAdapter;
import com.example.pocketexpenses.recyclers.TransactionsAdapter;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ChooseAccountActivity extends AppCompatActivity {

    private ActivityChooseAccountBinding binding;
    private AccountTypeViewModel oAccountTypeVM;
    private TransactionInputViewModel oTransactionInputVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oAccountTypeVM = new ViewModelProvider(this).get(AccountTypeViewModel.class);
        oTransactionInputVM = new ViewModelProvider(this).get(TransactionInputViewModel.class);

        FragmentManager manager = getSupportFragmentManager();
        ChooseAccountFragment defaultFragment = ChooseAccountFragment.newInstance();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, defaultFragment, "Choose Accounts");
        transaction.commit();

        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}