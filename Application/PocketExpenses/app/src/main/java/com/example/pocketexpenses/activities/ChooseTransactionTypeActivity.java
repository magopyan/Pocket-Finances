package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.databinding.ActivityChooseTransactionTypeBinding;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.onclicklisteners.OnTransactionTypeClickListener;
import com.example.pocketexpenses.recyclers.ChooseTransactionTypeAdapter;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;

import java.util.List;

public class ChooseTransactionTypeActivity extends AppCompatActivity implements OnTransactionTypeClickListener {

    private ActivityChooseTransactionTypeBinding binding;
    private TransactionTypeViewModel oTransactionTypeVM;
    private TransactionInputViewModel oTransactionInputVM;
    private RecyclerView oTransactionTypesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseTransactionTypeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oTransactionTypesRV = binding.recyclerViewHolder;

        oTransactionTypeVM = new ViewModelProvider(this).get(TransactionTypeViewModel.class);
        oTransactionInputVM = new ViewModelProvider(this).get(TransactionInputViewModel.class);

        ChooseTransactionTypeAdapter adapter = new ChooseTransactionTypeAdapter(oTransactionTypeVM, this::onClickTransactionType);
        oTransactionTypeVM.getAllTransactionDirectionWithTypesAndSubtypes().observe(this, new Observer<List<TransactionDirectionWithTypesAndSubtypes>>() {
            @Override
            public void onChanged(List<TransactionDirectionWithTypesAndSubtypes> transactionDirectionWithTypesAndSubtypes) {
                adapter.setData(transactionDirectionWithTypesAndSubtypes);
            }
        });
        oTransactionTypesRV.setAdapter(adapter);
        oTransactionTypesRV.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClickTransactionType(TransactionType tranType) {
        oTransactionInputVM.setTransactionType(tranType);
        onBackPressed();
    }
}