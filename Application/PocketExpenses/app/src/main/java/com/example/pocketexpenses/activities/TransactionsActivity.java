package com.example.pocketexpenses.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.ActivityTransactionsBinding;
import com.example.pocketexpenses.fragments.TransactionsListFragment;

public class TransactionsActivity extends AppCompatActivity {

    private ActivityTransactionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FragmentManager manager = getSupportFragmentManager();
        TransactionsListFragment fragment = TransactionsListFragment.newInstance();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainerTransactions, fragment, "Transactions");
        transaction.commit();
    }
}
