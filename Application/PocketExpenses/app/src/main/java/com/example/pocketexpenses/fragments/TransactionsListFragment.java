package com.example.pocketexpenses.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.ViewModel.AccountTypeViewModel;
import com.example.pocketexpenses.ViewModel.TransactionTypeViewModel;
import com.example.pocketexpenses.ViewModel.TransactionViewModel;
import com.example.pocketexpenses.databinding.FragmentAccountsListBinding;
import com.example.pocketexpenses.databinding.FragmentTransactionsListBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.recyclers.AccountsAdapter;
import com.example.pocketexpenses.recyclers.TransactionsAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsListFragment extends Fragment {

    private FragmentTransactionsListBinding binding;
    private RecyclerView oTransactionsRV;
    private TransactionViewModel oTransactionViewModel;
    private TransactionTypeViewModel oTransactionTypeViewModel;
    private AccountTypeViewModel oAccountTypeViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    public TransactionsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment TransactionsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsListFragment newInstance() {
        TransactionsListFragment fragment = new TransactionsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oTransactionsRV = binding.recyclerTransactions;

        oTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        oTransactionTypeViewModel = new ViewModelProvider(this).get(TransactionTypeViewModel.class);
        oAccountTypeViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);

        TransactionsAdapter adapter = new TransactionsAdapter(oTransactionViewModel, oTransactionViewModel.getAllTransactions().getValue(),
                oTransactionTypeViewModel.getAllTransactionSubtype().getValue(), oAccountTypeViewModel.getAllAccounts().getValue());
        oTransactionsRV.setAdapter(adapter);
        oTransactionsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        oTransactionViewModel.getAllTransactions().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> oListTransactions) {
                adapter.setTransactionsData(oListTransactions);
            }
        });

        oTransactionTypeViewModel.getAllTransactionSubtype().observe(getViewLifecycleOwner(), new Observer<List<TransactionSubtype>>() {
            @Override
            public void onChanged(@Nullable final List<TransactionSubtype> oListTransactionSubtypes) {
                adapter.setTransactionSubtypesData(oListTransactionSubtypes);
            }
        });

        oAccountTypeViewModel.getAllAccounts().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable final List<Account> oListAccounts) {
                adapter.setAccountsData(oListAccounts);
            }
        });
    }
}