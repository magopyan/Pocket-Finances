package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.databinding.ActivityChooseAccountTypeBinding;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.onclicklisteners.OnAccountTypeClickListener;
import com.example.pocketexpenses.recyclers.ChooseAccountTypeAdapter;
import com.example.pocketexpenses.viewmodels.AccountInputViewModel;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChooseAccountTypeActivity extends AppCompatActivity implements OnAccountTypeClickListener {

    private ActivityChooseAccountTypeBinding binding;
    private AccountTypeViewModel oAccountTypeViewModel;
    private AccountInputViewModel oAccountInputViewModel;
    private RecyclerView oAccountTypesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAccountTypeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oAccountTypesRV = binding.recyclerViewHolder2;

        oAccountTypeViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);
        oAccountInputViewModel = new ViewModelProvider(this).get(AccountInputViewModel.class);

        ChooseAccountTypeAdapter adapter = new ChooseAccountTypeAdapter(oAccountTypeViewModel, this::onClickAccountType);

        oAccountTypeViewModel.getAllAccountTypes().observe(this, new Observer<List<AccountType>>() {
                    @Override
                    public void onChanged(List<AccountType> accountTypes) {
                        adapter.setData(accountTypes);
                    }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        oAccountTypesRV.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        oAccountTypesRV.setLayoutManager(layoutManager);
        oAccountTypesRV.setAdapter(adapter);
    }

    @Override
    public void onClickAccountType(AccountType oAccountType) {
        oAccountInputViewModel.setAccountType(oAccountType);
        onBackPressed();
    }
}