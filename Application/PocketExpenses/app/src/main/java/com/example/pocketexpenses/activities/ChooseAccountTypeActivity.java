package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.databinding.ActivityChooseAccountTypeBinding;
import com.example.pocketexpenses.databinding.ActivityChooseTransactionTypeBinding;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.onclicklisteners.OnAccountTypeClickListener;
import com.example.pocketexpenses.recyclers.ChooseAccountTypeAdapter;
import com.example.pocketexpenses.recyclers.ChooseTransactionTypeAdapter;
import com.example.pocketexpenses.viewmodels.AccountTypeInputViewModel;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;

import java.util.List;

public class ChooseAccountTypeActivity extends AppCompatActivity implements OnAccountTypeClickListener {

    private ActivityChooseAccountTypeBinding binding;
    private AccountTypeViewModel oAccountTypeViewModel;
    private AccountTypeInputViewModel oAccountTypeInputViewModel;
    private RecyclerView oAccountTypesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAccountTypeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oAccountTypesRecyclerView = binding.recyclerViewHolder2;

        oAccountTypeViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);
        oAccountTypeInputViewModel = new ViewModelProvider(this).get(AccountTypeInputViewModel.class);

        ChooseAccountTypeAdapter adapter = new ChooseAccountTypeAdapter(oAccountTypeViewModel, this::onClickAccountType);

        oAccountTypeViewModel.getAllAccountTypes().observe(this, new Observer<List<AccountType>>() {
            @Override
            public void onChanged(List<AccountType> oListAccountType) {
                adapter.setData(oListAccountType);
            }
        });
        oAccountTypesRecyclerView.setAdapter(adapter);
        oAccountTypesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClickAccountType(AccountType oAccountType) {
        oAccountTypeInputViewModel.setAccountType(oAccountType);
        onBackPressed();
    }
}