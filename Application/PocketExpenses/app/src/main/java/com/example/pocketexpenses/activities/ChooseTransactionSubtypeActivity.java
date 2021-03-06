package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.pocketexpenses.databinding.ActivityChooseTransactionSubtypeBinding;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.onclicklisteners.OnTransactionSubtypeClickListener;
import com.example.pocketexpenses.recyclers.ChooseTransactionSubtypeAdapter;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;

import java.util.List;

public class ChooseTransactionSubtypeActivity extends AppCompatActivity implements OnTransactionSubtypeClickListener {

    private ActivityChooseTransactionSubtypeBinding binding;
    private TransactionTypeViewModel oTransactionTypeVM;
    private TransactionInputViewModel oTransactionInputVM;
    private RecyclerView oTransactionSubtypesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseTransactionSubtypeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oTransactionSubtypesRV = binding.recyclerViewHolder;

        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oTransactionTypeVM = new ViewModelProvider(this).get(TransactionTypeViewModel.class);
        oTransactionInputVM = new ViewModelProvider(this).get(TransactionInputViewModel.class);

        int tranTypeId = getIntent().getIntExtra("TransactionTypeID", -1);
        ChooseTransactionSubtypeAdapter adapter = new ChooseTransactionSubtypeAdapter(oTransactionTypeVM, this::onClickTransactionSubtype, tranTypeId);

        if(tranTypeId != -1) { // -> Choose Subtype for Expense (list only subtypes from chosen type
            // Send double nested relationhip
            oTransactionTypeVM.getAllTransactionDirectionWithTypesAndSubtypes().observe(this, new Observer<List<TransactionDirectionWithTypesAndSubtypes>>() {
                @Override
                public void onChanged(List<TransactionDirectionWithTypesAndSubtypes> transactionDirectionWithTypesAndSubtypes) {
                    adapter.setDataForExpense(transactionDirectionWithTypesAndSubtypes);
                }
            });
        }
        else {  // -> Choose Subtype for Income (list all subtypes for income)
            oTransactionTypeVM.getAllTransactionDirectionWithTypesAndSubtypes().observe(this, new Observer<List<TransactionDirectionWithTypesAndSubtypes>>() {
                @Override
                public void onChanged(List<TransactionDirectionWithTypesAndSubtypes> transactionDirectionWithTypesAndSubtypes) {
                    adapter.setDataForIncome(transactionDirectionWithTypesAndSubtypes);
                }
            });
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        oTransactionSubtypesRV.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        oTransactionSubtypesRV.setLayoutManager(layoutManager);
        oTransactionSubtypesRV.setAdapter(adapter);
    }


    @Override
    public void onClickTransactionSubtype(TransactionSubtype tranSubtype) {
        oTransactionInputVM.setTransactionSubtype(tranSubtype);
        onBackPressed();
    }
}