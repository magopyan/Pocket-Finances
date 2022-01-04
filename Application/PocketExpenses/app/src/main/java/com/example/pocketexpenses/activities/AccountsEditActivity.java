package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityAccountsEditBinding;
import com.example.pocketexpenses.databinding.ActivityAccountsInputBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.fragments.AccountEditFragment;
import com.example.pocketexpenses.fragments.AccountInputFragment;
import com.example.pocketexpenses.onclicklisteners.OnEditAccountListener;
import com.example.pocketexpenses.viewholders.AccountViewHolder;
import com.example.pocketexpenses.viewmodels.AccountInputViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class AccountsEditActivity extends AppCompatActivity implements OnEditAccountListener {

    private ActivityAccountsEditBinding binding;
    private AccountInputViewModel oAccountInputVM;
    private AccountEditFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oAccountInputVM = new ViewModelProvider(this).get(AccountInputViewModel.class);

        Intent receivedIntent = getIntent();
        Account oAccount = receivedIntent.getParcelableExtra("AccountItem");

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
        AccountEditFragment defaultFragment;
        if(manager.findFragmentByTag("Input") == null) {
            defaultFragment = AccountEditFragment.newInstance(oAccount);
            fragment = defaultFragment;
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragmentContainer, defaultFragment, "Edit");
            transaction.commit();
        }
    }

    @Override
    public void EditAccount(Account oAccount) {
        Intent intent = new Intent();
        intent.putExtra("AccountItem", oAccount);

        //setResult(REQUEST_EDIT_CODE, intent);
        finish();
    }
}