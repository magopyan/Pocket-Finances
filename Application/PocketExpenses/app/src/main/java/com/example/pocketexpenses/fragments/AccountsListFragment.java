package com.example.pocketexpenses.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketexpenses.activities.AccountInputActivity;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.databinding.FragmentAccountsListBinding;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.recyclers.AccountsAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountsListFragment extends Fragment implements View.OnClickListener {

    private FragmentAccountsListBinding binding;
    private RecyclerView oAccountsRV;
    private AccountTypeViewModel oViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public AccountsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AccountsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountsListFragment newInstance() {
        AccountsListFragment fragment = new AccountsListFragment();
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
        binding = FragmentAccountsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oAccountsRV = binding.recyclerAccounts;
        binding.fabAddAccount.setOnClickListener(this::onClick);

        oViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);

        AccountsAdapter adapter = new AccountsAdapter(oViewModel);
        oViewModel.getAllAccountTypesWithAccounts().observe(getViewLifecycleOwner(), new Observer<List<AccountTypeWithAccounts>>() {
            @Override
            public void onChanged(@Nullable final List<AccountTypeWithAccounts> accTypesWithAccounts) {
                adapter.setData(accTypesWithAccounts);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        oAccountsRV.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        oAccountsRV.setLayoutManager(layoutManager);
        oAccountsRV.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), AccountInputActivity.class);
        intent.putExtra("topBarTitle", "Add new Account");
        startActivity(intent);
    }
}