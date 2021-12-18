package com.example.pocketexpenses.activities;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.pocketexpenses.recyclers.AccountsAdapter;
import com.example.pocketexpenses.recyclers.TransactionsAdapter;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class ChooseAccountActivity extends AppCompatActivity {

    private ActivityChooseAccountBinding binding;
    private AccountTypeViewModel oAccountTypeVM;
    private TransactionInputViewModel oTransactionInputVM;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        oAccountTypeVM = new ViewModelProvider(this).get(AccountTypeViewModel.class);
        oTransactionInputVM = new ViewModelProvider(this).get(TransactionInputViewModel.class);

        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        recyclerView = binding.recyclerView;
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//       /* AccountsAdapter adapter = new AccountsAdapter()*/

//        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), TransactionInputActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("account", "Cashhhh");
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = oAccountTypeVM.getAccountByID(1);
                oTransactionInputVM.setAccount(account);
                onBackPressed();
            }
        });
    }
}