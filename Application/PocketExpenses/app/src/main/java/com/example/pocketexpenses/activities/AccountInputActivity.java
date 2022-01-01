package com.example.pocketexpenses.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityAccountsInputBinding;
import com.example.pocketexpenses.fragments.AccountInputFragment;
import com.example.pocketexpenses.viewmodels.AccountInputViewModel;
import com.google.android.material.appbar.MaterialToolbar;


public class AccountInputActivity extends AppCompatActivity {

    private ActivityAccountsInputBinding binding;
    private AccountInputViewModel oAccountInputVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountsInputBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oAccountInputVM = new ViewModelProvider(this).get(AccountInputViewModel.class);

        Intent receivedIntent = getIntent();


        MaterialToolbar topAppBar = binding.topAppBar;
        String title = receivedIntent.getStringExtra("topBarTitle");
        topAppBar.setTitle(title);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oAccountInputVM.reset();
                onBackPressed();
            }
        });


        FragmentManager manager = getSupportFragmentManager();
        AccountInputFragment defaultFragment;
        if(manager.findFragmentByTag("Input") == null) {
            defaultFragment = AccountInputFragment.newInstance();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragmentContainer, defaultFragment, "Input");
            transaction.commit();
        }

    }
}
